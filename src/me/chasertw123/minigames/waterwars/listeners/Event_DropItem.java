package me.chasertw123.minigames.waterwars.listeners;

import me.chasertw123.minigames.waterwars.Main;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;

/**
 * Created by Scott Hiett on 4/22/2017.
 */
public class Event_DropItem implements Listener {

    public Event_DropItem(){
        Bukkit.getServer().getPluginManager().registerEvents(this, Main.getInstance());
    }

    @EventHandler
    public void drop(PlayerDropItemEvent e){
        if (Main.getUserManager().get(e.getPlayer().getUniqueId()).isDead())
            e.setCancelled(true);
    }

}
