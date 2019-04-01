package me.chasertw123.minigames.waterwars.listeners;

import me.chasertw123.minigames.core.user.data.achievements.Achievement;
import me.chasertw123.minigames.core.user.data.stats.Stat;
import me.chasertw123.minigames.core.utils.items.AbstractItem;
import me.chasertw123.minigames.core.utils.items.Items;
import me.chasertw123.minigames.shared.framework.ServerGameType;
import me.chasertw123.minigames.waterwars.Main;
import me.chasertw123.minigames.waterwars.game.GameState;
import me.chasertw123.minigames.waterwars.game.loops.Loop_Respawn;
import me.chasertw123.minigames.waterwars.guis.Gui_PlayerTeleporter;
import me.chasertw123.minigames.waterwars.users.User;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

import static org.bukkit.Bukkit.getServer;

/**
 * Created by Scott Hiett on 4/21/2017.
 */
public class Event_PlayerDeath implements Listener {

    public Event_PlayerDeath(){
        getServer().getPluginManager().registerEvents(this, Main.getInstance());
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void death(PlayerDeathEvent e) {
        User user = Main.getUserManager().get(e.getEntity().getUniqueId());

        user.getCoreUser().incrementStat(Stat.WATER_WARS_SOLO_DEATHS);
        user.setDead(true);
        user.setLives(user.getLives() - 1);

        for (Player player : Bukkit.getOnlinePlayers()) {

            User u = Main.getUserManager().get(player.getUniqueId());

            if (u.getCoreUser().getCurrentGui() instanceof Gui_PlayerTeleporter)
                u.getCoreUser().setCurrentGui(new Gui_PlayerTeleporter(u));
        }

        user.getPlayer().setPlayerListName(ChatColor.DARK_GRAY + "[" +  (user.getLives() == 0 ? ChatColor.RED + "DEAD" : ChatColor.YELLOW + "" + user.getLives()) + ChatColor.DARK_GRAY + "] "
                + user.getCoreUser().getRank().getRankColor() + user.getPlayer().getName());

        String playerName = user.getCoreUser().getRank().getRankColor() + user.getPlayer().getName() + ChatColor.GRAY;
        e.setDeathMessage(ChatColor.GRAY + e.getDeathMessage().replace(user.getPlayer().getName(), playerName) + "!");
        e.setKeepInventory(false);
        e.setKeepLevel(false);

        if (e.getEntity().getKiller() != null) {

            User killer =  Main.getUserManager().get(e.getEntity().getKiller().getUniqueId());

            e.setDeathMessage(user.getCoreUser().getRank().getRankColor() + user.getPlayer().getName() + ChatColor.GRAY + " was killed by "
                    + killer.getCoreUser().getRank().getRankColor() + killer.getPlayer().getName() + ChatColor.GRAY + "!");
            killer.getCoreUser().incrementStat(Stat.WATER_WARS_SOLO_KILLS);
            killer.setKills(killer.getKills() + 1);
            killer.getCoreUser().giveCoins(ServerGameType.WATER_WARS, 5, true);
            killer.getCoreUser().giveExperience(5);

            if (killer.getKills() >= 24)
                killer.getCoreUser().unlockAchievement(Achievement.WATER_WARS_DOMINATION);

            if (killer.getPlayer().getItemInHand() == null || killer.getPlayer().getItemInHand().getType() == Material.AIR)
                killer.getCoreUser().unlockAchievement(Achievement.WATER_WARS_ONE_PUNCH);
        }

        else if (e.getEntity().getKiller() == null && (user.getPlayer().getLocation().getBlock().getType() == Material.STATIONARY_WATER
                || user.getPlayer().getLocation().getBlock().getType() == Material.WATER))
            user.getCoreUser().unlockAchievement(Achievement.WATER_WARS_WATERY_GRAVE);

        for (Player player : getServer().getOnlinePlayers())
            if (player.getUniqueId() != user.getPlayer().getUniqueId())
                player.hidePlayer(user.getPlayer());

        Location deathLocation = e.getEntity().getLocation();
        Main.getInstance().getServer().getScheduler().scheduleSyncDelayedTask(Main.getInstance(), () -> {
            user.getPlayer().spigot().respawn();
            user.getPlayer().teleport(deathLocation);
            user.getPlayer().spigot().setCollidesWithEntities(false);
            user.getPlayer().setGameMode(GameMode.ADVENTURE);
            user.getPlayer().setAllowFlight(true);
            user.getPlayer().setFlying(true);
        }, 1L);

        if (user.getLives() != 0) {
            new Loop_Respawn(user);
            return;
        }

        user.setPlace(Main.getGameManager().getAliveUsers().size() + 1);
        user.getCoreUser().addActivity(ordinal(user.getPlace()) + " Water Wars (solo)");

        for (Player player : getServer().getOnlinePlayers())
            if (player.getUniqueId() != user.getPlayer().getUniqueId())
                player.sendMessage(ChatColor.LIGHT_PURPLE + user.getPlayer().getName() + ChatColor.WHITE + " has been eliminated!");

        user.getCoreUser().giveExperience(10);
        user.getCoreUser().giveCoins(ServerGameType.WATER_WARS, 10, false); // Participation
        user.getCoreUser().incrementStat(Stat.WATER_WARS_PLAYTIME, (int) (System.currentTimeMillis() - user.getJoinTime()) / 1000);

        user.getPlayer().sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "You have been eliminated!");
        Main.getInstance().getServer().getScheduler().scheduleSyncDelayedTask(Main.getInstance(), () -> {

            new AbstractItem(Items.PLAY_AGAIN.getItemStack(), user.getCoreUser(), 7, (type) -> user.getCoreUser().addToQueue("water-wars"));
            new AbstractItem(Items.RETURN_TO_HUB.getItemStack(), user.getCoreUser(), 8, (type) -> user.getCoreUser().sendToServer("hub"));
            new AbstractItem(Items.PLAYER_TELEPORTER.getItemStack(), user.getCoreUser(), 0, (type) -> {

                if (Main.getGameManager().getGameState() != GameState.GAME) {
                    user.getPlayer().sendMessage( ChatColor.RED + "The game is over... Why are you trying to teleport to someone?");
                    return;
                }

                new Gui_PlayerTeleporter(user);
            });
        }, 2L);

        Main.getGameManager().checkGame();
    }

    private String ordinal(int i) {

        String[] sufixes = new String[] { "th", "st", "nd", "rd", "th", "th", "th", "th", "th", "th" };
        switch (i % 100) {

            case 11:
            case 12:
            case 13:
                return i + "th";

            default:
                return i + sufixes[i % 10];

        }
    }
}
