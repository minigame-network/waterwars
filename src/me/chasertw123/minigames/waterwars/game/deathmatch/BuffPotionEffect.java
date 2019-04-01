package me.chasertw123.minigames.waterwars.game.deathmatch;

import org.bukkit.potion.PotionEffectType;

public class BuffPotionEffect {

    private PotionEffectType potionEffectType;
    private int maxLevelAllowed;

    public BuffPotionEffect(PotionEffectType type, int maxLevelAllowed) {
        this.maxLevelAllowed = maxLevelAllowed;
        this.potionEffectType = type;
    }

    public int getMaxLevelAllowed() {
        return maxLevelAllowed;
    }

    public PotionEffectType getPotionEffectType() {
        return potionEffectType;
    }

}
