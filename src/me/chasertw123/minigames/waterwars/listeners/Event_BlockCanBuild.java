package me.chasertw123.minigames.waterwars.listeners;

import me.chasertw123.minigames.waterwars.Main;
import me.chasertw123.minigames.waterwars.users.User;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockCanBuildEvent;

/**
 * Created by Chase on 8/14/2017.
 */
public class Event_BlockCanBuild implements Listener {

    public Event_BlockCanBuild(){
        Bukkit.getServer().getPluginManager().registerEvents(this, Main.getInstance());
    }

    @EventHandler
    public void onBlockCanBuild(BlockCanBuildEvent e) {

        if (e.isBuildable())
            return;

        for (Player player : Bukkit.getOnlinePlayers()) {
            User user = Main.getUserManager().get(player.getUniqueId());

            if (!user.isDead() || !user.isFullDead())
                continue;

            if (e.getBlock().getLocation() == player.getLocation() || e.getBlock().getLocation() == player.getEyeLocation()) {
                e.setBuildable(true);
                break;
            }
        }
    }
}
