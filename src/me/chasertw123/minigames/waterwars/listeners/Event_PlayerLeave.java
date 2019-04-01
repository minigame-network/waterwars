package me.chasertw123.minigames.waterwars.listeners;

import me.chasertw123.minigames.core.user.data.stats.Stat;
import me.chasertw123.minigames.waterwars.Main;
import me.chasertw123.minigames.waterwars.game.GameState;
import me.chasertw123.minigames.waterwars.users.User;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

/**
 * Created by Scott Hiett on 4/21/2017.
 */
public class Event_PlayerLeave implements Listener {

    public Event_PlayerLeave() {
        Bukkit.getServer().getPluginManager().registerEvents(this, Main.getInstance());
    }

    @EventHandler
    public void onPlayerQuit(me.chasertw123.minigames.core.event.UserQuitEvent event) {

        User user = Main.getUserManager().get(event.getUser().getUUID());
        if (!user.save())
            Bukkit.getLogger().severe("Failed to save " + user.getPlayer().getName() + "'s data!");

        else
            System.out.println("Saved " + user.getPlayer().getName() + "'s Data!");
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerQuit(PlayerQuitEvent e) {

        e.setQuitMessage(null); // TODO: Change leave message depending on state of the game

        if (Main.getGameManager().getGameState() == GameState.STARTING)
            Main.getCageManager().removeCage(e.getPlayer());

        User user = Main.getUserManager().get(e.getPlayer().getUniqueId());
        if (Main.getGameManager().getGameState() != GameState.LOBBY && !user.isFullDead())
            user.getCoreUser().incrementStat(Stat.WATER_WARS_PLAYTIME, (int) (System.currentTimeMillis() - user.getJoinTime()) / 1000);

        Main.getUserManager().remove(e.getPlayer().getUniqueId());
    }

}
