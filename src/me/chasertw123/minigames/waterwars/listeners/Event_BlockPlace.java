package me.chasertw123.minigames.waterwars.listeners;

import me.chasertw123.minigames.waterwars.Main;
import me.chasertw123.minigames.waterwars.game.deathmatch.WorldDisintegration;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

import java.util.Random;

/**
 * Created by Chase on 7/31/2017.
 */
public class Event_BlockPlace implements Listener {

    public Event_BlockPlace(){
        Bukkit.getServer().getPluginManager().registerEvents(this, Main.getInstance());
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent e) {

        if (Main.getGameManager().getMap().getMaxY() < e.getBlock().getLocation().getBlockY()) {
            e.setCancelled(true);
            return;
        }

        Location center = Main.getGameManager().getMap().getMapCenter().clone();
        if (Main.getGameManager().getMap().getMaxRadius() + center.getBlockX() <= e.getBlock().getLocation().getBlockX()
                || Main.getGameManager().getMap().getMaxRadius() + center.getBlockZ() <= e.getBlock().getLocation().getBlockZ())
            e.setCancelled(true);

        if (Main.getGameManager().getWorldDisintegration() != null) {
            WorldDisintegration worldDisintegration = Main.getGameManager().getWorldDisintegration();

            if (worldDisintegration.getCurrentRadius() + center.getBlockX() <= e.getBlock().getLocation().getBlockX()
                    || worldDisintegration.getCurrentRadius() + center.getBlockZ() <= e.getBlock().getLocation().getBlockZ()) {
                Location l = e.getBlockPlaced().getLocation();

                Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Main.getInstance(), () -> l.getBlock().setType(Material.AIR),
                        20 * new Random().nextInt(3) + 4);
            }

        }

    }
}
