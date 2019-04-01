package me.chasertw123.minigames.waterwars.unlocks.perks.defaults;

import me.chasertw123.minigames.waterwars.unlocks.perks.Perk;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

/**
 * Created by Chase on 5/2/2017.
 */
public class Wolverine extends Perk {

    public Wolverine() {
        super("Wolverine", 5, 7500, new ItemStack(Material.GOLDEN_APPLE));
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
