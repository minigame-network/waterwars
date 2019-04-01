package me.chasertw123.minigames.waterwars.listeners;

import me.chasertw123.minigames.waterwars.Main;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

/**
 * Created by Scott Hiett on 4/22/2017.
 */
public class Event_DamageEntityByEntity implements Listener {

    public Event_DamageEntityByEntity(){
        Bukkit.getServer().getPluginManager().registerEvents(this, Main.getInstance());
    }

    @EventHandler
    public void onEntityDamageByEntity(EntityDamageByEntityEvent e){

        if (!(e.getEntity() instanceof Player))
            return;

        if (e.getDamager() instanceof Player && Main.getUserManager().get(e.getDamager().getUniqueId()).isDead()) {
            e.setCancelled(true);
            return;
        }
    }

}
