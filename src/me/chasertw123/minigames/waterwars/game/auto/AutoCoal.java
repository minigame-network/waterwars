package me.chasertw123.minigames.waterwars.game.auto;

import org.bukkit.Material;
import org.bukkit.block.Furnace;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.FurnaceSmeltEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.FurnaceInventory;
import org.bukkit.inventory.ItemStack;

public class AutoCoal implements Listener {

	private ItemStack coal;
	
	public AutoCoal() {
		coal = new ItemStack(Material.COAL, 64);
	}
	
	@EventHandler
	public void open(InventoryOpenEvent e) {
		if (e.getInventory() instanceof FurnaceInventory)
			e.getInventory().setItem(1, coal);
	}
	
	@EventHandler
	public void click(InventoryClickEvent e) {
		if (e.getInventory() instanceof FurnaceInventory)
			if (e.getSlot() == 1)
				e.setCancelled(true);
	}
	
	@EventHandler
	public void smelt(FurnaceSmeltEvent e) {
		if (e.getBlock() instanceof Furnace) {
			Furnace f = (Furnace) e.getBlock();
			FurnaceInventory fi = f.getInventory();
			fi.setFuel(coal);
		}
	}

}
