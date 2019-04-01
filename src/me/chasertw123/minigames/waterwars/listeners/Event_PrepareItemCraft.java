package me.chasertw123.minigames.waterwars.listeners;

import me.chasertw123.minigames.waterwars.Main;
import me.chasertw123.minigames.waterwars.game.GameState;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.inventory.ItemStack;

/**
 * Created by Chase on 8/6/2017.
 */
public class Event_PrepareItemCraft implements Listener {

    public Event_PrepareItemCraft() {
        Bukkit.getServer().getPluginManager().registerEvents(this, Main.getInstance());
    }

    @EventHandler
    public void onPrepareItemCraft(PrepareItemCraftEvent e) {

        if (Main.getGameManager().getGameState() != GameState.GAME)
            return;

        Material type = e.getRecipe().getResult().getType();
        if (type == Material.BOAT || type == Material.CHEST || type == Material.TRAPPED_CHEST)
            e.getInventory().setResult(new ItemStack(Material.AIR));
    }
}
