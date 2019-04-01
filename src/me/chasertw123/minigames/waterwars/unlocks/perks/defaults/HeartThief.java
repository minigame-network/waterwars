package me.chasertw123.minigames.waterwars.unlocks.perks.defaults;

import me.chasertw123.minigames.waterwars.unlocks.perks.Perk;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

/**
 * Created by Chase on 5/2/2017.
 */
public class HeartThief extends Perk {

    private static final int[] STEAL_CHANCE = {5, 7, 10};

    public HeartThief() {
        super("Heart Thief", 3, 5000, new ItemStack(Material.INK_SACK, 1, (short) 1));
    }

    @Override
    protected String getDescription(int level) {
        return getStealChance(level) + "% to gain an additional heart after you get a kill.";
    }

    @Override
    protected String[] getUnlockDescriptions() {
        return new String[] {"getting 500 kills", "getting 1000 kills"};
    }

    public int getStealChance(int level) {
        return STEAL_CHANCE[level - 1];
    }
}
