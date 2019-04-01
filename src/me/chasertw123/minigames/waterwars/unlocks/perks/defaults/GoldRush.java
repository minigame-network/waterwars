package me.chasertw123.minigames.waterwars.unlocks.perks.defaults;

import me.chasertw123.minigames.waterwars.unlocks.perks.Perk;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

/**
 * Created by Chase on 5/2/2017.
 */
public class GoldRush extends Perk {

    public GoldRush() {
        super("Gold Rush", 5, 1000, new ItemStack(Material.GOLD_PICKAXE));
    }

    @Override
    protected String getDescription(int level) {
        return "Start the game with " + (5 * level) + " seconds of haste.";
    }

    @Override
    protected String[] getUnlockDescriptions() {
        return new String[] {"breaking 100 blocks", "breaking 500 blocks", "breaking 2500 blocks", "breaking 10000 blocks",};
    }
}
