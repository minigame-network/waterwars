package me.chasertw123.minigames.waterwars.game.auto;

import me.chasertw123.minigames.core.user.data.stats.Stat;
import me.chasertw123.minigames.waterwars.Main;
import me.chasertw123.minigames.waterwars.game.GameState;
import me.chasertw123.minigames.waterwars.users.User;
import org.bukkit.DyeColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.enchantment.EnchantItemEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.EnchantingInventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.Dye;

import java.util.ArrayList;
import java.util.List;

public class AutoLapis implements Listener{

	private List<EnchantingInventory> inventories = new ArrayList<>();
	private ItemStack lapis;
	
	public AutoLapis(){
		Dye d = new Dye();
		d.setColor(DyeColor.BLUE);
		this.lapis = d.toItemStack();
		this.lapis.setAmount(64);
	}
	
	@EventHandler
	public void openInventoryEvent(InventoryOpenEvent e) {
		if (e.getInventory() instanceof EnchantingInventory) {
				e.getInventory().setItem(1, this.lapis);
				inventories.add((EnchantingInventory) e.getInventory());
		}
	}

	@EventHandler
	public void closeInventoryEvent(InventoryCloseEvent e) {
		if (e.getInventory() instanceof EnchantingInventory && inventories.contains(e.getInventory())) {
			e.getInventory().setItem(1, null);
			inventories.remove((EnchantingInventory) e.getInventory());
		}
	}

	@EventHandler
	public void inventoryClickEvent(InventoryClickEvent e) {
		if (e.getClickedInventory() instanceof EnchantingInventory && inventories.contains(e.getInventory()) && e.getSlot() == 1)
					e.setCancelled(true);
	}

	@EventHandler
	public void enchantItemEvent(EnchantItemEvent e) {
		if (inventories.contains(e.getInventory()))
			e.getInventory().setItem(1, this.lapis);

		User user = Main.getUserManager().get(e.getEnchanter().getUniqueId());
		if (Main.getGameManager().getGameState() == GameState.GAME && !user.isDead())
			user.getCoreUser().incrementStat(Stat.WATER_WARS_ITEMS_ENCHANTED);
	}
	
}
