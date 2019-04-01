package me.chasertw123.minigames.waterwars.listeners;

import me.chasertw123.minigames.core.user.data.stats.Stat;
import me.chasertw123.minigames.waterwars.Main;
import me.chasertw123.minigames.waterwars.game.GameState;
import me.chasertw123.minigames.waterwars.users.User;
import org.bukkit.Bukkit;
import org.bukkit.block.Chest;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryOpenEvent;

/**
 * Created by Chase on 8/6/2017.
 */
public class Event_InventoryOpen implements Listener {

    public Event_InventoryOpen(){
        Bukkit.getServer().getPluginManager().registerEvents(this, Main.getInstance());
    }

    @EventHandler
    public void onInventoryOpen(InventoryOpenEvent e) {

        User user = Main.getUserManager().get(e.getPlayer().getUniqueId());
        if (Main.getGameManager().getGameState() == GameState.GAME && e.getInventory().getHolder() instanceof Chest && !user.isDead()
                && !user.getChestsOpened().contains(((Chest) e.getInventory().getHolder()).getLocation())) {
            user.getChestsOpened().add(((Chest) e.getInventory().getHolder()).getLocation());
            user.getCoreUser().incrementStat(Stat.WATER_WARS_CHESTS_OPENED);
        }
    }
}
