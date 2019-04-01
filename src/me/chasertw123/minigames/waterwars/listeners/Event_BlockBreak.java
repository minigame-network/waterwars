package me.chasertw123.minigames.waterwars.listeners;

import me.chasertw123.minigames.waterwars.Main;
import me.chasertw123.minigames.waterwars.game.GameState;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

/**
 * Created by Chase on 8/6/2017.
 */
public class Event_BlockBreak implements Listener {

    public Event_BlockBreak() {
        Bukkit.getServer().getPluginManager().registerEvents(this, Main.getInstance());
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent e) {
        if (Main.getGameManager().getGameState() == GameState.GAME && e.getBlock().getType() == Material.CHEST) {
            e.setCancelled(true);
            e.getPlayer().sendMessage(ChatColor.RED + "That chest seemed to just regenerate!");
        }
    }
}
