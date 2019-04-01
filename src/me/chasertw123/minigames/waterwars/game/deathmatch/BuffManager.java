package me.chasertw123.minigames.waterwars.game.deathmatch;

import org.bukkit.potion.PotionEffectType;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class BuffManager {

    private static BuffPotionEffect[] buffPotionEffects = new BuffPotionEffect[]{
            new BuffPotionEffect(PotionEffectType.INVISIBILITY, 0),
            new BuffPotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 1),
            new BuffPotionEffect(PotionEffectType.JUMP, 1),
            new BuffPotionEffect(PotionEffectType.SPEED, 1),
            new BuffPotionEffect(PotionEffectType.INCREASE_DAMAGE, 0),
            new BuffPotionEffect(PotionEffectType.SATURATION, 0),
            new BuffPotionEffect(PotionEffectType.REGENERATION, 0),
            new BuffPotionEffect(PotionEffectType.ABSORPTION, 0),
            new BuffPotionEffect(PotionEffectType.HEALTH_BOOST, 0),
    };

    private List<BuffPotionEffect> randomizedArray;

    public BuffManager() {
        randomizedArray = Arrays.asList(buffPotionEffects);
        Collections.shuffle(randomizedArray);
    }

    public List<BuffPotionEffect> getRandomizedArray() {
        return randomizedArray;
    }

}
