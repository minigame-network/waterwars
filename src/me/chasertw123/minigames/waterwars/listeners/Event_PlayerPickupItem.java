package me.chasertw123.minigames.waterwars.listeners;

import me.chasertw123.minigames.waterwars.Main;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerPickupItemEvent;

/**
 * Created by Scott Hiett on 4/22/2017.
 */
public class Event_PlayerPickupItem implements Listener {

    public Event_PlayerPickupItem(){
        Bukkit.getServer().getPluginManager().registerEvents(this, Main.getInstance());
    }

    @EventHandler
    public void pickup(PlayerPickupItemEvent e){

        Player p = e.getPlayer();

        if(Main.getUserManager().get(p.getUniqueId()).isDead())
            e.setCancelled(true);
    }

}
