package me.chasertw123.minigames.waterwars.unlocks.perks.defaults;

import me.chasertw123.minigames.waterwars.unlocks.perks.Perk;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

/**
 * Created by Chase on 5/2/2017.
 */
public class Scavenger extends Perk {

    public Scavenger() {
        super("Scavenger", 3, 1000, new ItemStack(Material.ARROW));
    }

    @Override
    protected String[] getUnlockDescriptions() {
        return new String[0];
    }

    @Override
    protected String getDescription(int level) {
        return null;
    }
}
