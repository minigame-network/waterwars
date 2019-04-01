package me.chasertw123.minigames.waterwars.unlocks.perks.defaults;

import me.chasertw123.minigames.waterwars.unlocks.perks.Perk;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

/**
 * Created by Chase on 5/2/2017.
 */
public class SmeltingPick extends Perk {

    public SmeltingPick() {
        super("Smelting Pick", 1, 2500, new ItemStack(Material.IRON_PICKAXE));
    }

    @Override
    protected String getDescription(int level) {
        return "Instantly smelt ores you mine with a pickaxe.";
    }

    @Override
    protected String[] getUnlockDescriptions() {
        return new String[0];
    }
}
