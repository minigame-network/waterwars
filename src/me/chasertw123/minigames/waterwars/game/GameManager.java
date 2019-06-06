package me.chasertw123.minigames.waterwars.game;

import me.chasertw123.minigames.core.api.v2.CoreAPI;
import me.chasertw123.minigames.core.user.data.stats.Stat;
import me.chasertw123.minigames.core.utils.items.cItemStack;
import me.chasertw123.minigames.shared.framework.GeneralServerStatus;
import me.chasertw123.minigames.waterwars.Main;
import me.chasertw123.minigames.waterwars.game.chests.ChestFiller;
import me.chasertw123.minigames.waterwars.game.deathmatch.BuffManager;
import me.chasertw123.minigames.waterwars.game.deathmatch.BuffPotionEffect;
import me.chasertw123.minigames.waterwars.game.deathmatch.WorldDisintegration;
import me.chasertw123.minigames.waterwars.game.loops.*;
import me.chasertw123.minigames.waterwars.game.maps.GameMap;
import me.chasertw123.minigames.waterwars.game.scoreboards.SB_Game;
import me.chasertw123.minigames.waterwars.users.User;
import me.chasertw123.minigames.wws.unlocks.kits.Kit;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Chase on 2/18/2017.
 */
public class GameManager {

    public static final int MIN_PLAYERS = 2, MAX_PLAYERS = 12;

    private GameLoop gameLoop;
    private boolean inCages = true;
    private GameState gameState = GameState.LOBBY;
    private GameMap map;
    private WorldDisintegration worldDisintegration = null;
    private ChestFiller chestFiller;

    public GameManager() {
        gameLoop = new Loop_Lobby();

        CoreAPI.getServerDataManager().updateServerState(GeneralServerStatus.LOBBY, MAX_PLAYERS);
    }

    public void loadMapData(GameMap map){
        this.map = map;
    }

    public void startGame() {

        CoreAPI.getServerDataManager().updateServerState(GeneralServerStatus.INGAME, MAX_PLAYERS);

        gameLoop = new Loop_Game();
        new Loop_CageRelease();

        this.gameState = GameState.GAME;
        this.chestFiller = new ChestFiller();
        this.chestFiller.fill();

        int id = 0;
        for (Player player : Bukkit.getServer().getOnlinePlayers()) {

            User user = Main.getUserManager().get(player.getUniqueId());

            user.setJoinTime(System.currentTimeMillis());
            user.getCoreUser().setScoreboard(new SB_Game(user));
            user.getCoreUser().incrementStat(Stat.WATER_WARS_SOLO_GAMES_PLAYED);
            user.getCoreUser().getAbstractItems().clear();

            user.getPlayer().setPlayerListName(ChatColor.DARK_GRAY + "[" +  (user.getLives() == 0 ? ChatColor.RED + "DEAD" : ChatColor.YELLOW + "" + user.getLives()) + ChatColor.DARK_GRAY + "] "
                    + user.getCoreUser().getRank().getRankColor() + user.getPlayer().getName());

            player.closeInventory();
            player.getInventory().clear();

            Kit k = user.getSelectedKit();
            for(ItemStack i : k.getItems())
                player.getInventory().addItem(i);

            player.getInventory().addItem(new cItemStack(Material.COMPASS).setDisplayName(ChatColor.GREEN + "Player Tracker"));
            user.setIsland(id);

            Main.getCageManager().buildCage(player, map.getIslandData(id).getSpawn());

            player.teleport(map.getIslandData(id).getSpawn().clone().add(0.5, 0, 0.5));

            Vector dir = map.getMapCenter().clone().subtract(player.getEyeLocation()).toVector();
            Location newLocation = player.getLocation().setDirection(dir);
            player.teleport(newLocation);

            id++;

//            Main.getInstance().database.savePlayerData(user); // TODO: Save player data
        }
    }

    /**
     * Called every second in checking loop,
     * and called on a player death.
     */
    public void checkGame() {

        if (getAliveUsers().size() == 1) {
            gameState = GameState.ENDING;
            gameLoop.cancel();
            new Loop_GameOver(getAliveUsers().get(0));
            endGame();
        }

        else if (getAliveUsers().size() == 0 && gameState != GameState.ENDING) {
            Bukkit.broadcastMessage(ChatColor.RED + "An error has occurred and as such the game will now auto-end.");
            endGame();
        }
    }

    public List<User> getInPlayers(){
        List<User> aliveUsers = new ArrayList<>();
        for (Player player : Bukkit.getOnlinePlayers())
            if (Main.getUserManager().has(player.getUniqueId()) && (!Main.getUserManager().get(player.getUniqueId()).isFullDead() || !Main.getUserManager().get(player.getUniqueId()).isDead()))
                aliveUsers.add(Main.getUserManager().get(player.getUniqueId()));

        return aliveUsers;
    }

    public List<User> getAliveUsers() {
        List<User> aliveUsers = new ArrayList<>();
        for (Player player : Bukkit.getOnlinePlayers())
            if (Main.getUserManager().has(player.getUniqueId()) && !Main.getUserManager().get(player.getUniqueId()).isFullDead())
                aliveUsers.add(Main.getUserManager().get(player.getUniqueId()));

        return aliveUsers;
    }

    public List<User> getAliveUsers(User playerToIgnore) {
        List<User> aliveUsers = new ArrayList<>();
        for (Player player : Bukkit.getOnlinePlayers())
            if (Main.getUserManager().has(player.getUniqueId()) && !Main.getUserManager().get(player.getUniqueId()).isFullDead() && player.getUniqueId() != playerToIgnore.getPlayer().getUniqueId())
                aliveUsers.add(Main.getUserManager().get(player.getUniqueId()));

        return aliveUsers;
    }

    public List<User> getDeadUsers(){
        List<User> deadUser = new ArrayList<>();
        for(Player player : Bukkit.getOnlinePlayers())
            if(Main.getUserManager().get(player.getUniqueId()).isFullDead())
                deadUser.add(Main.getUserManager().get(player.getUniqueId()));

        return deadUser;
    }

    public void startDeathmatch(){
        // Enter deathmatch

        worldDisintegration = new WorldDisintegration(Main.getMapManager().getGameWorld(), map.getMapCenter(), map.getMaxRadius(), map.getWaterY(), map.getMaxY());

        BuffManager buffManager = new BuffManager();

        for(User user : Main.getUserManager().toCollection()) {
            user.sendMessage(ChatColor.YELLOW + "The Deathmatch has Started.");

            if(!user.isFullDead()) { // They're alive
                int buffs = user.getLives() + user.getKills();

                user.setLives(1);

                user.sendMessage(ChatColor.AQUA + "You have " + ChatColor.ITALIC + buffs + ChatColor.AQUA + " buffs.");

                for(int i = 0; i < buffs; i++) {
                    BuffPotionEffect buffPotionEffect = buffManager.getRandomizedArray().get(i);

                    if(buffPotionEffect.getMaxLevelAllowed() >= buffs) {
                        user.getPlayer().addPotionEffect(new PotionEffect(buffPotionEffect.getPotionEffectType(), Integer.MAX_VALUE, buffPotionEffect.getMaxLevelAllowed() - (buffs - i)));

                        break;
                    } else {
                        i += buffPotionEffect.getMaxLevelAllowed();

                        user.getPlayer().addPotionEffect(new PotionEffect(buffPotionEffect.getPotionEffectType(), Integer.MAX_VALUE, buffPotionEffect.getMaxLevelAllowed()));
                    }
                }
            }
        }
    }

    public void endGame() {
        if(worldDisintegration != null) {
            worldDisintegration.cancel();

            worldDisintegration = null;
        }

        CoreAPI.getServerDataManager().updateServerState(GeneralServerStatus.RESTARTING, MAX_PLAYERS);

        Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Main.getInstance(), () -> {
            for (Player player : Bukkit.getServer().getOnlinePlayers())
                CoreAPI.getUser(player).sendToServer("hub");
        }, 20 * 15);

        Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Main.getInstance(), () -> {

//            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "restart");

            // DYNAMIC SERVER SYSTEM CODE WOOHOO
            CoreAPI.getServerDataManager().updateServerState(GeneralServerStatus.DELETE, MAX_PLAYERS);
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "stop");

            }, 20 * 25);
    }

    public GameState getGameState() {
        return gameState;
    }

    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }

    public boolean inCages() {
        return inCages;
    }

    public void setInCages(boolean inCages) {
        this.inCages = inCages;
    }

    public GameLoop getGameLoop() {
        return this.gameLoop;
    }

    public ChestFiller getChestFiller() {
        return this.chestFiller;
    }

    public WorldDisintegration getWorldDisintegration() {
        return this.worldDisintegration;
    }

    public GameMap getMap() {
        return this.map;
    }
}
