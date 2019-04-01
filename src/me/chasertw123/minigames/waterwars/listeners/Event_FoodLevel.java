package me.chasertw123.minigames.waterwars.listeners;

import me.chasertw123.minigames.waterwars.Main;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.FoodLevelChangeEvent;

/**
 * Created by Scott Hiett on 4/22/2017.
 */
public class Event_FoodLevel implements Listener {

    public Event_FoodLevel(){
        Bukkit.getServer().getPluginManager().registerEvents(this, Main.getInstance());
    }

    @EventHandler
    public void food(FoodLevelChangeEvent e) {

        if (Main.getUserManager().get(e.getEntity().getUniqueId()).isDead()) {
            e.setCancelled(true);
            e.setFoodLevel(20);
        }
    }

}
