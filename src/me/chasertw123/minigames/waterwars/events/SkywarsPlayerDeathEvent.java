package me.chasertw123.minigames.waterwars.events;

import me.chasertw123.minigames.waterwars.users.User;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 * Created by Chase on 6/2/2017.
 */
public class SkywarsPlayerDeathEvent extends Event {

    private static final HandlerList handlers = new HandlerList();
    private User killed, killer;

    public SkywarsPlayerDeathEvent(User killed, User killer) {
        this.killed = killed;
        this.killer = killer;
    }

    public User getKilled() {
        return killed;
    }

    public User getKiller() {
        return killer;
    }

    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

}
