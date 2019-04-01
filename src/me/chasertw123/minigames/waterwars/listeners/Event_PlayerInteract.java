package me.chasertw123.minigames.waterwars.listeners;

import me.chasertw123.minigames.waterwars.Main;
import me.chasertw123.minigames.waterwars.users.User;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.SmallFireball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

/**
 * Created by Scott Hiett on 4/21/2017.
 */
public class Event_PlayerInteract implements Listener {

    public Event_PlayerInteract(){
        Bukkit.getServer().getPluginManager().registerEvents(this, Main.getInstance());
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent e) {

        User user = Main.getUserManager().get(e.getPlayer().getUniqueId());
        if ((user.isDead() || user.isFullDead()) && e.getAction() == Action.RIGHT_CLICK_BLOCK && (e.getClickedBlock().getType() == Material.CHEST || e.getClickedBlock().getType() == Material.FURNACE
                || e.getClickedBlock().getType() == Material.BREWING_STAND || e.getClickedBlock().getType() == Material.ENCHANTMENT_TABLE || e.getClickedBlock().getType() == Material.ENDER_CHEST
                || e.getClickedBlock().getType() == Material.ANVIL || e.getClickedBlock().getType() == Material.BURNING_FURNACE || e.getClickedBlock().getType() == Material.TRAPPED_CHEST
                || e.getClickedBlock().getType() == Material.WORKBENCH || e.getClickedBlock().getType() == Material.JUKEBOX || e.getClickedBlock().getType() == Material.NOTE_BLOCK
                || e.getClickedBlock().getType() == Material.TRAP_DOOR)) {

            e.setCancelled(true);
        }

        if(e.getPlayer().getItemInHand() != null && e.getPlayer().getItemInHand().getType() == Material.FIREBALL && e.getAction() == Action.RIGHT_CLICK_AIR) {
            e.getPlayer().launchProjectile(SmallFireball.class).setVelocity(e.getPlayer().getLocation().getDirection().multiply(0.5));

            e.getPlayer().getInventory().remove(new ItemStack(Material.FIREBALL, 1));
            e.getPlayer().updateInventory();

            e.setCancelled(true);
        }

    }

}
