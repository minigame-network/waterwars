package me.chasertw123.minigames.waterwars.game.loops;

import me.chasertw123.minigames.core.api.misc.Title;
import me.chasertw123.minigames.waterwars.Main;
import me.chasertw123.minigames.waterwars.game.GameState;
import me.chasertw123.minigames.waterwars.users.User;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.text.DecimalFormat;

/**
 * Created by Chase on 8/23/2017.
 */
public class Loop_PlayerTracker extends GameLoop {

    DecimalFormat df = new DecimalFormat("0.0");

    public Loop_PlayerTracker() {
        super(1, 3);
    }

    @Override
    public void run() {

        if (Main.getGameManager().getGameState() != GameState.GAME) {
            this.cancel();
            return;
        }

        for (Player player : Bukkit.getOnlinePlayers()) {

            User user = Main.getUserManager().get(player.getUniqueId());
            if (user.isDead() || user.isFullDead())
                continue;

            Player target = null;
            double distance = 0;
            int radiusToCheck = Main.getGameManager().map.getMax_radius();
            for (Entity entity : player.getNearbyEntities(radiusToCheck, Main.getGameManager().map.getMax_y(), radiusToCheck)) {

                if (!(entity instanceof Player))
                    continue;

                User targetUser = Main.getUserManager().get(entity.getUniqueId());
                if (targetUser.isDead() || targetUser.isFullDead())
                    continue;

                double distanceToEntity = player.getLocation().distanceSquared(entity.getLocation());
                if (target == null || distance > distanceToEntity) {
                    target = (Player) entity;
                    distance = distanceToEntity;
                }
            }

            if (target == null)
                continue;

            player.setCompassTarget(target.getLocation());

            if (player.getItemInHand() != null && player.getItemInHand().getType() == Material.COMPASS) {
                Title.sendActionbar(player, ChatColor.WHITE + "" + ChatColor.BOLD + "Nearest Target: " + ChatColor.YELLOW + target.getName()
                        + ChatColor.WHITE + "     " + ChatColor.BOLD + "Distance: " + ChatColor.YELLOW + df.format(player.getLocation().distance(target.getLocation()))
                        + ChatColor.WHITE + "     " + ChatColor.BOLD + "Height: " + ChatColor.YELLOW + df.format((player.getLocation().getY() - target.getLocation().getY())));

                Main.getInstance().getServer().getScheduler().scheduleSyncDelayedTask(Main.getInstance(), () -> {
                    if (player.getItemInHand() == null || player.getItemInHand().getType() != Material.COMPASS)
                        Title.sendActionbar(player, " ");
                }, 1L);
            }
        }

    }

}
