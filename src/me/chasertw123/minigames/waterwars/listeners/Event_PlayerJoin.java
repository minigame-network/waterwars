package me.chasertw123.minigames.waterwars.listeners;

import me.chasertw123.minigames.core.api.v2.CoreAPI;
import me.chasertw123.minigames.core.utils.items.AbstractItem;
import me.chasertw123.minigames.core.utils.items.Items;
import me.chasertw123.minigames.core.utils.items.cItemStack;
import me.chasertw123.minigames.shared.framework.ServerSetting;
import me.chasertw123.minigames.waterwars.Main;
import me.chasertw123.minigames.waterwars.game.GameManager;
import me.chasertw123.minigames.waterwars.game.GameState;
import me.chasertw123.minigames.waterwars.game.scoreboards.SB_Lobby;
import me.chasertw123.minigames.waterwars.guis.Gui_CageSelector;
import me.chasertw123.minigames.waterwars.guis.Gui_KitSelector;
import me.chasertw123.minigames.waterwars.guis.Gui_MapVote;
import me.chasertw123.minigames.waterwars.users.User;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.potion.PotionEffect;

/**
 * Created by Chase on 2/19/2017.
 */
public class Event_PlayerJoin implements Listener {

    public Event_PlayerJoin() {
        Main.getInstance().getServer().getPluginManager().registerEvents(this, Main.getInstance());
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerJoinEvent(PlayerJoinEvent e) {

        if (Main.getGameManager().getGameState() != GameState.LOBBY) {

            e.setJoinMessage(null);
            e.getPlayer().teleport(Main.getGameManager().getMap().getMapCenter());

            User user = new User(CoreAPI.getUser(e.getPlayer()));
            Main.getUserManager().add(user);

            user.setDead(true);
            user.setLives(0);
            user.getPlayer().setHealthScale(20D);
            user.getPlayer().setHealthScaled(false);
            user.getPlayer().getInventory().setArmorContents(null);
            user.getPlayer().getInventory().clear();
            user.getPlayer().setGameMode(GameMode.ADVENTURE);
            user.getPlayer().setAllowFlight(true);
            user.getPlayer().setFlying(true);
            user.getPlayer().spigot().setCollidesWithEntities(false);

            user.getCoreUser().setServerSetting(ServerSetting.DAMAGE, false);
            user.getCoreUser().setServerSetting(ServerSetting.HUNGER, false);
            user.getCoreUser().setServerSetting(ServerSetting.ITEM_DROPPING, false);

            user.getPlayer().setPlayerListName(ChatColor.DARK_GRAY + "[" +  (user.getLives() == 0 ? ChatColor.RED + "DEAD" : ChatColor.YELLOW + "" + user.getLives()) + ChatColor.DARK_GRAY + "] "
                    + user.getCoreUser().getRank().getRankColor() + user.getPlayer().getName());

            for (PotionEffect effect : e.getPlayer().getActivePotionEffects())
                e.getPlayer().removePotionEffect(effect.getType());

            e.getPlayer().setFireTicks(0);
            e.getPlayer().setLevel(0);
            e.getPlayer().setExp(0F);
            e.getPlayer().resetMaxHealth();
            e.getPlayer().setHealth(e.getPlayer().getMaxHealth());

            e.getPlayer().getInventory().setItem(0, new cItemStack(Material.COMPASS, ChatColor.GREEN + "Player Menu"));
            e.getPlayer().getInventory().setItem(8, new cItemStack(Material.NETHER_STAR, ChatColor.GREEN + "Leave Game"));

            Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Main.getInstance(), () -> {
                for (Player player : Bukkit.getOnlinePlayers())
                    if (player.getUniqueId() != user.getPlayer().getUniqueId())
                        player.hidePlayer(user.getPlayer());
            }, 2L);

            return;
        }

        me.chasertw123.minigames.core.user.User pp = CoreAPI.getUser(e.getPlayer());
        User user = new User(pp);

        Main.getUserManager().add(user);

        pp.setScoreboard(new SB_Lobby(user));

        net.md_5.bungee.api.ChatColor nameColor = pp.getRank().getRankColor();
        if (!pp.getRank().isStaff() && pp.isDeluxe())
            nameColor = net.md_5.bungee.api.ChatColor.GOLD;

        e.setJoinMessage(nameColor + e.getPlayer().getName() + ChatColor.GRAY + " joined the game! (" + Main.getUserManager().toCollection().size() + "/" + GameManager.MAX_PLAYERS + ")");

        user.getPlayer().spigot().setCollidesWithEntities(true);
        e.getPlayer().setMaxHealth(20D);
        e.getPlayer().setHealthScale(20D);
        e.getPlayer().setHealthScaled(false);
        e.getPlayer().setAllowFlight(false);
        e.getPlayer().setFlying(false);
        e.getPlayer().setFireTicks(0);
        e.getPlayer().getInventory().setArmorContents(null);
        e.getPlayer().setLevel(0);
        e.getPlayer().setExp(0F);
        e.getPlayer().resetMaxHealth();
        e.getPlayer().setHealth(e.getPlayer().getMaxHealth());
        e.getPlayer().setGameMode(GameMode.ADVENTURE);
        e.getPlayer().setFoodLevel(20);
        e.getPlayer().teleport(Main.getMapManager().getLobbyWorld().getSpawnLocation());
        e.getPlayer().getInventory().clear();

        e.getPlayer().setPlayerListName(user.getCoreUser().getRank().getRankColor() + e.getPlayer().getName());

        for (PotionEffect effect : e.getPlayer().getActivePotionEffects())
            e.getPlayer().removePotionEffect(effect.getType());

        new AbstractItem(Items.MAP_VOTING.getItemStack(), pp, 0, (type) -> new Gui_MapVote(user));
        new AbstractItem(new cItemStack(Material.ARMOR_STAND, ChatColor.GREEN + "Kit Selector " + ChatColor.GRAY + "(Right Click)"), pp, 1, (type) -> new Gui_KitSelector(user));
        new AbstractItem(new cItemStack(Material.GLASS, ChatColor.LIGHT_PURPLE + "Cage Selector " + ChatColor.GRAY + "(Right Click)"), pp, 2, (type) -> new Gui_CageSelector(user));
        new AbstractItem(Items.RETURN_TO_HUB.getItemStack(), pp, 8, (type) -> {
            pp.sendPrefixedMessage(ChatColor.GREEN + "Sending to Hub...");
            pp.sendToServer("hub");
        });
    }
}
