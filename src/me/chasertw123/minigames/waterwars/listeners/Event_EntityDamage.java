package me.chasertw123.minigames.waterwars.listeners;

import me.chasertw123.minigames.waterwars.Main;
import me.chasertw123.minigames.waterwars.game.GameState;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

/**
 * Created by Chase on 7/30/2017.
 */
public class Event_EntityDamage implements Listener {

    public Event_EntityDamage(){
        Bukkit.getServer().getPluginManager().registerEvents(this, Main.getInstance());
    }

    @EventHandler
    public void onEntityDamage(EntityDamageEvent e) {

        if (e.getEntity() instanceof Player && Main.getUserManager().get(e.getEntity().getUniqueId()).isDead()) {
            e.setCancelled(true);
            return;
        }

        if (e.getEntity() instanceof Player && e.getCause() == EntityDamageEvent.DamageCause.FALL && Main.getGameManager().getGameState() == GameState.GAME) {
            e.setCancelled(true);
            return;
        }
    }
}
