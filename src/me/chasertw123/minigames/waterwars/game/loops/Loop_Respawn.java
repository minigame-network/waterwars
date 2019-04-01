package me.chasertw123.minigames.waterwars.game.loops;

import me.chasertw123.minigames.core.api.misc.Title;
import me.chasertw123.minigames.core.utils.items.cItemStack;
import me.chasertw123.minigames.waterwars.Main;
import me.chasertw123.minigames.waterwars.game.GameState;
import me.chasertw123.minigames.waterwars.guis.Gui_PlayerTeleporter;
import me.chasertw123.minigames.waterwars.users.User;
import me.chasertw123.minigames.wws.unlocks.kits.Kit;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import static org.bukkit.Bukkit.getServer;

/**
 * Created by Chase on 8/3/2017.
 */
public class Loop_Respawn extends GameLoop {

    private User user;

    public Loop_Respawn(User user) {
        super(12, 20);
        this.user = user;
    }

    @Override
    public void run() {

        Title.sendActionbar(user.getPlayer(), ChatColor.AQUA + "" + ChatColor.BOLD + "(!) " + ChatColor.GREEN + "You will respawn in " + interval + "s...");

        if (--interval == 0) {

            if (Main.getGameManager().getGameState() == GameState.ENDING) {
                this.cancel();
                return;
            }

            for (Player player : getServer().getOnlinePlayers())
                if (player.getUniqueId() != user.getPlayer().getUniqueId())
                    player.showPlayer(user.getPlayer());

            Main.getGameManager().chestFiller.fillIsland(Main.getGameManager().map.getIslandData(user.getIsland()));

            user.getPlayer().getInventory().clear();

            Kit k = user.getSelectedKit();
            for(ItemStack i : k.getItems())
                user.getPlayer().getInventory().addItem(i);

            user.getPlayer().spigot().setCollidesWithEntities(true);
            user.getPlayer().getInventory().addItem(new cItemStack(Material.COMPASS).setDisplayName(ChatColor.GREEN + "Player Tracker " + ChatColor.GRAY + "(Hold for Info)"));
            user.getPlayer().setGameMode(GameMode.SURVIVAL);
            user.getPlayer().teleport(Main.getGameManager().map.getIslandData(user.getIsland()).getSpawn());
            user.setDead(false);

            if (user.getPlayer().isFlying())
                user.getPlayer().setFlying(false);

            user.getPlayer().setAllowFlight(false);
            user.getPlayer().setFireTicks(0);

            for (PotionEffect effect : user.getPlayer().getActivePotionEffects())
                user.getPlayer().removePotionEffect(effect.getType());

            Title.sendActionbar(user.getPlayer(), null);
            user.getPlayer().sendMessage(ChatColor.RED + "You have " + ChatColor.WHITE + user.getLives() + ChatColor.RED + (user.getLives() == 1 ? " life" : " lives") + " left!");
            user.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 200, 3));
            user.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, 200, 3));
            user.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.ABSORPTION, 200, 4));

            for (Player player : Bukkit.getOnlinePlayers())
                if (Main.getUserManager().get(player.getUniqueId()).getCoreUser().getCurrentGui() instanceof Gui_PlayerTeleporter)
                    Main.getUserManager().get(player.getUniqueId()).getCoreUser().setCurrentGui(new Gui_PlayerTeleporter(Main.getUserManager().get(player.getUniqueId())));

            user.getPlayer().closeInventory();

            this.cancel();
        }

    }
}
