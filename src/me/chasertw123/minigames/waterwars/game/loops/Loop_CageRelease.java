package me.chasertw123.minigames.waterwars.game.loops;

import me.chasertw123.minigames.core.api.misc.Title;
import me.chasertw123.minigames.core.api.v2.CoreAPI;
import me.chasertw123.minigames.shared.framework.ServerSetting;
import me.chasertw123.minigames.waterwars.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

/**
 * Created by Scott Hiett on 4/21/2017.
 */
public class Loop_CageRelease extends GameLoop {

    public Loop_CageRelease(){
        super(10, 20L);
    }

    @Override
    public void run() {

        if (interval > 5)
            for (Player player : Bukkit.getOnlinePlayers())
                Title.sendTitle(player,0, 20, 0, ChatColor.GREEN + "" + interval);

        else if (interval <= 5 && interval > 1)
            for (Player player : Bukkit.getOnlinePlayers()) {
                player.playSound(player.getLocation(), Sound.NOTE_PIANO, 1F, 0.1F);
                Title.sendTitle(player, 0, 20, 0, ChatColor.YELLOW + "" + ChatColor.BOLD + interval);
            }

        else if (interval == 1)
            for (Player player : Bukkit.getOnlinePlayers()) {
                player.playSound(player.getLocation(), Sound.NOTE_PIANO, 1F, 2F);
                Title.sendTitle(player, 0, 20, 0, ChatColor.RED + "" + ChatColor.BOLD + interval);
            }

        if (interval-- == 0) {

            Main.getCageManager().removePlayersCages();
            Main.getGameManager().setInCages(false);

            new Loop_WaterDamage();
            new Loop_PlayerTracker();

            for (Player player : Bukkit.getOnlinePlayers()) {

                me.chasertw123.minigames.core.user.User pp = CoreAPI.getUser(player);

                pp.setServerSetting(ServerSetting.DAMAGE, true);
                pp.setServerSetting(ServerSetting.HUNGER, true);
                pp.setServerSetting(ServerSetting.ITEM_DROPPING, true);

                Title.sendTitle(player,0, 20, 0, ChatColor.GREEN + "" + ChatColor.BOLD + "GO");
                Bukkit.getServer().getScheduler().runTaskLater(Main.getInstance(), () -> Title.sendTitle(player, 0, 1, 0, " "), 20L);

                player.setGameMode(GameMode.SURVIVAL);
                player.setHealth(player.getMaxHealth());
                player.setFoodLevel(20);
                player.setSaturation(14.4F);
                player.playSound(player.getLocation(), Sound.ENDERDRAGON_GROWL, 1F, 1F);
            }

            this.cancel();
        }
    }
}
