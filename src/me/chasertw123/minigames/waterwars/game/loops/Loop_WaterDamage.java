package me.chasertw123.minigames.waterwars.game.loops;

import me.chasertw123.minigames.waterwars.Main;
import me.chasertw123.minigames.waterwars.users.User;
import org.bukkit.Material;
import org.bukkit.scheduler.BukkitRunnable;

/**
 * Created by Chase on 7/31/2017.
 */
public class Loop_WaterDamage extends BukkitRunnable {

    public Loop_WaterDamage() {
        this.runTaskTimer(Main.getInstance(), 0L, 1L);
    }

    @Override
    public void run() {

        for (User user : Main.getUserManager().toCollection()) {

            if (!(user.getPlayer().getLocation().getBlock().getType() == Material.WATER
                    || user.getPlayer().getLocation().getBlock().getType() == Material.STATIONARY_WATER) || user.isDead()
                    || (user.getLastWaterDamage() + 1500) > System.currentTimeMillis())
                continue;

            user.getPlayer().damage(3);
            user.setLastWaterDamage(System.currentTimeMillis());
        }

    }
}
