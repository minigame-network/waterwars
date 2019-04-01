package me.chasertw123.minigames.waterwars.listeners;

import me.chasertw123.minigames.waterwars.Main;
import me.chasertw123.minigames.waterwars.game.GameState;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityExplodeEvent;

/**
 * Created by Chase on 8/6/2017.
 */
public class Event_EntityExplode implements Listener {

    public Event_EntityExplode() {
        Bukkit.getServer().getPluginManager().registerEvents(this, Main.getInstance());
    }

    @EventHandler
    public void onEntityExplose(EntityExplodeEvent e) {
        if (Main.getGameManager().getGameState() == GameState.GAME)
            e.blockList().removeIf( b -> b.getType() == Material.CHEST);
    }
}
