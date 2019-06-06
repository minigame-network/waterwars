package me.chasertw123.minigames.waterwars.listeners;

import me.chasertw123.minigames.waterwars.Main;
import me.chasertw123.minigames.waterwars.game.deathmatch.WorldDisintegration;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockFormEvent;

import java.util.Random;

public class Event_BlockForm implements Listener {

    public Event_BlockForm() {
        Bukkit.getServer().getPluginManager().registerEvents(this, Main.getInstance());
    }

    @EventHandler
    public void onBlockForm(BlockFormEvent e) {
        if(Main.getGameManager().getWorldDisintegration() != null) {
            Location center = Main.getGameManager().getMap().getMapCenter().clone();

            WorldDisintegration worldDisintegration = Main.getGameManager().getWorldDisintegration();

            if (worldDisintegration.getCurrentRadius() + center.getBlockX() <= e.getBlock().getLocation().getBlockX()
                    || worldDisintegration.getCurrentRadius() + center.getBlockZ() <= e.getBlock().getLocation().getBlockZ()) {
                Location l = e.getBlock().getLocation();

                Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Main.getInstance(), () -> l.getBlock().setType(Material.AIR),
                        20 * new Random().nextInt(3) + 4);
            }

        }
    }

}
