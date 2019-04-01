package me.chasertw123.minigames.waterwars.unlocks.perks.defaults;

import me.chasertw123.minigames.waterwars.unlocks.perks.Perk;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

/**
 * Created by Chase on 5/2/2017.
 */
public class ExplosiveTech extends Perk {

    private static final int[] DAMAGE_REDUCTION = {10, 15, 20, 25, 35};

    public ExplosiveTech() {
        super("Explosive Tech", 5, 2500, new ItemStack(Material.TNT));
    }

    @Override
    protected String getDescription(int level) {
        return "You take " + getDamageReduction(level) + " % less damage from explosions!";
    }

    @Override
    protected String[] getUnlockDescriptions() {
        return new String[] {"setting off 50 tnt", "setting off 250 tnt", "setting off 1000 tnt", "setting off 5000 tnt"};
    }

    public int getDamageReduction(int level) {
        return DAMAGE_REDUCTION[level - 1];
    }
}
