package me.chasertw123.minigames.waterwars.unlocks.perks;

import me.chasertw123.minigames.waterwars.unlocks.perks.defaults.*;

/**
 * Created by Chase on 5/2/2017.
 */
public enum PerkType {

    SMELTING_PICK(new SmeltingPick()),
    HEART_THIEF(new HeartThief()),
    FLASH(new Flash()),
    TOUGH_GUY(new ToughGuy()),
    COLLATERAL(new Collateral()),
    SCAVENGER(new Scavenger()),
    WOLVERINE(new Wolverine()),
    EXPLOSIVE_TECH(new ExplosiveTech()),
    SOFT_LANDING(new SoftLanding()),
    MINERS_LUCK(new MinersLuck()),
    GOLD_RUSH(new GoldRush());

    private Perk perk;

    PerkType(Perk perk) {
        this.perk = perk;
    }

    public Perk getPerkClass() {
        return perk;
    }
}
