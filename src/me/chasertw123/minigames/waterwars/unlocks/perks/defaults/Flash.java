package me.chasertw123.minigames.waterwars.unlocks.perks.defaults;

import me.chasertw123.minigames.waterwars.unlocks.perks.Perk;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

/**
 * Created by Chase on 5/2/2017.
 */
public class Flash extends Perk {

    public Flash() {
        super("Flash", 5,2500, new ItemStack(Material.SUGAR));
    }

    @Override
    protected String getDescription(int level) {
        return "Start the game with " + 5 * level + " seconds of swiftness!";
    }

    @Override
    protected String[] getUnlockDescriptions() {
        return new String[] {"opening 100 chests", "opening 250 chests", "opening 500 chests", "opening 1000 chests"};
    }


}
