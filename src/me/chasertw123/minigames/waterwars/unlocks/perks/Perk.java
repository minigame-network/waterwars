package me.chasertw123.minigames.waterwars.unlocks.perks;

import me.chasertw123.minigames.core.utils.items.cItemStack;
import me.chasertw123.minigames.waterwars.users.User;
import org.bukkit.ChatColor;
import org.bukkit.inventory.ItemStack;

/**
 * Created by Chase on 5/2/2017.
 */
public abstract class Perk {

    private String displayName;
    private int maxLevel, buyPrice;

    private ItemStack display;

    public Perk(String displayName, int maxLevel, int buyPrice, ItemStack display) {
        this.displayName = displayName;
        this.maxLevel = maxLevel;
        this.buyPrice = buyPrice;
        this.display = display;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String howToUnlock(int level) {
        return getUnlockDescriptions()[level - 2];
    }

    public int getMaxLevel() {
        return maxLevel;
    }

    public int getBuyPrice() {
        return buyPrice;
    }

    public ItemStack getDisplay(User user) {

        int perkLevel = 0;// user.getPerkLevel(getEnum());
        cItemStack i = new cItemStack(display);

        if (perkLevel < 1) {

            i.setDisplayName(ChatColor.RED + displayName);

            i.addLore("");
            i.addFancyLore(getDescription(perkLevel + 1), ChatColor.GRAY.toString());
            i.addLore("", "" + ChatColor.RED + "Unlocked with " + getBuyPrice() + " Coins");
        }

        i.setDisplayName(ChatColor.GREEN + displayName + ChatColor.GRAY + " [" + ChatColor.YELLOW + perkLevel + ChatColor.GRAY + "]");

        i.addLore("");
        i.addFancyLore(getDescription(perkLevel), ChatColor.GRAY.toString());
        i.addLore("");

        if (perkLevel < maxLevel)
            i.addFancyLore("Unlock level " + (perkLevel + 1) + " by " + howToUnlock(perkLevel), ChatColor.RED.toString());

        else
            i.addLore(ChatColor.GREEN + "MAXED LEVEL");

        return i;
    }

    protected abstract String getDescription(int level);

    protected abstract String[] getUnlockDescriptions();

    private PerkType getEnum() {

        for (PerkType perkType : PerkType.values())
            if (perkType.getPerkClass().getDisplayName().equals(this.getDisplayName()))
                return perkType;

        return null;
    }
}
