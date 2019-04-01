package me.chasertw123.minigames.waterwars.listeners;

import me.chasertw123.minigames.waterwars.Main;
import me.chasertw123.minigames.waterwars.game.GameManager;
import me.chasertw123.minigames.waterwars.game.GameState;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerListPingEvent;

/**
 * Created by Scott Hiett on 4/21/2017.
 */
public class Event_ServerPing implements Listener {

    public Event_ServerPing(){
        Bukkit.getServer().getPluginManager().registerEvents(this, Main.getInstance());
    }

    @EventHandler
    public void motdRequest(ServerListPingEvent e){

        e.setMaxPlayers(GameManager.MAX_PLAYERS);

        if(Main.getGameManager().getGameState() == GameState.LOBBY)
            e.setMotd("LOBBY:" + GameManager.MAX_PLAYERS);
        else
            e.setMotd("INGAME");
    }

}
