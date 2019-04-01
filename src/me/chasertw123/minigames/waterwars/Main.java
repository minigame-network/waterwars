package me.chasertw123.minigames.waterwars;

import co.aikar.taskchain.BukkitTaskChainFactory;
import co.aikar.taskchain.TaskChain;
import co.aikar.taskchain.TaskChainFactory;
import me.chasertw123.minigames.core.api.v2.CoreAPI;
import me.chasertw123.minigames.shared.framework.ServerGameType;
import me.chasertw123.minigames.shared.framework.ServerType;
import me.chasertw123.minigames.waterwars.game.GameManager;
import me.chasertw123.minigames.waterwars.game.maps.MapManager;
import me.chasertw123.minigames.waterwars.game.votes.VoteManager;
import me.chasertw123.minigames.waterwars.listeners.EventManager;
import me.chasertw123.minigames.waterwars.unlocks.cages.CageManager;
import me.chasertw123.minigames.waterwars.users.UserManager;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Created by Chase on 2/18/2017.
 */
public class Main extends JavaPlugin {

    private static Main plugin;
    private static TaskChainFactory taskChainFactory;
    private static MapManager mapManager;
    private static UserManager userManager;
    private static VoteManager voteManager;
    private static GameManager gameManager;
    private static CageManager cageManager;

    @Override
    public void onLoad() {
        CoreAPI.getServerDataManager().setServerType(ServerType.MINIGAME);
        CoreAPI.getServerDataManager().setServerGameType(ServerGameType.WATER_WARS);
    }

    @Override
    public void onEnable() {
        plugin = this;

        taskChainFactory = BukkitTaskChainFactory.create(this);

        mapManager = new MapManager();
        userManager = new UserManager();
        voteManager = new VoteManager();
        gameManager = new GameManager();
        cageManager = new CageManager();

        new EventManager();
    }

    @Override
    public void onDisable() {
        plugin = null;
    }

    public static Main getInstance() {
        return plugin;
    }

    public static  <T> TaskChain<T> newChain() {
        return taskChainFactory.newChain();
    }

    public static MapManager getMapManager() {
        return mapManager;
    }

    public static UserManager getUserManager() {
        return userManager;
    }

    public static VoteManager getVoteManager() {
        return voteManager;
    }

    public static GameManager getGameManager() {
        return gameManager;
    }

    public static CageManager getCageManager() {
        return cageManager;
    }
}
