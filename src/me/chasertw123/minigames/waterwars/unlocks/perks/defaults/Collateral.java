package me.chasertw123.minigames.waterwars.unlocks.perks.defaults;

import me.chasertw123.minigames.waterwars.unlocks.perks.Perk;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

/**
 * Created by Chase on 5/2/2017.
 */
public class Collateral extends Perk {

    public Collateral() {
        super("Collateral", 3, 5000, new ItemStack(Material.GOLD_NUGGET));
    }

    @Override
    protected String getDescription(int level) {
        return "When you kill someone you have a " + (5 + (--level * 2.5)) + "% chance to get double coins!";
    }

    @Override
    protected String[] getUnlockDescriptions() {
        return new String[] {"getting 50 collaterals", "getting 250 collaterals"};
    }

//    @EventHandler
//    public void onSkyWarsPlayerDeath(SkywarsPlayerDeathEvent e) {
//
//        if (e.getKiller() != null && e.getKiller().hasPerkUnlocked(PerkType.COLLATERAL)) {
//
//            int roll = new Random().nextInt(1001), chance = (int) ((5 + ((e.getKiller().getPerkLevel(PerkType.COLLATERAL) - 1) * 2.5)) * 10);
//
//            if (roll <= chance) {
//                // TODO: Give double coins, increment collateral stat, send player message of doubled via collateral perk
//                e.getKiller().getParadisePlayer().getPlayer().sendMessage(Main.PREFIX + ChatColor.GOLD + "Collateral perk doubled the coins you received!");
//                return;
//            }
//        }
//    }
}
