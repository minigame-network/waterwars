package me.chasertw123.minigames.waterwars.game.loops;

import me.chasertw123.minigames.core.api.misc.Title;
import me.chasertw123.minigames.waterwars.Main;
import me.chasertw123.minigames.waterwars.game.GameManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

/**
 * Created by Chase on 2/19/2017.
 */
public class Loop_Lobby extends GameLoop {

    public Loop_Lobby() {
        super(30, 20);
    }

    @Override
    public void run() {

        Main.getMapManager().getLobbyWorld().setStorm(false);
        Main.getMapManager().getLobbyWorld().setThundering(false);

        int needed = GameManager.MIN_PLAYERS - Bukkit.getOnlinePlayers().size();
        if (needed > 0) {
            for (Player player : Bukkit.getOnlinePlayers())
                Title.sendActionbar(player, ChatColor.AQUA + "" + ChatColor.BOLD + "(!) " + ChatColor.GREEN + "Waiting for " + ChatColor.AQUA + needed + ChatColor.GREEN + " more player" + (needed > 1 ? "s" : ""));

        }

        else if (interval < 60 && GameManager.MIN_PLAYERS > Bukkit.getOnlinePlayers().size()) {
            setInterval(60);

            for (Player player: Bukkit.getOnlinePlayers())
                Title.sendActionbar(player, ChatColor.AQUA + "" + ChatColor.BOLD + "(!) " + ChatColor.GREEN + "Game canceled due to lack of players :(");
        }

        if (GameManager.MIN_PLAYERS <= Bukkit.getOnlinePlayers().size()) {
            if (Bukkit.getOnlinePlayers().size() >= (GameManager.MAX_PLAYERS * 0.75) && interval > 45) {
                setInterval(20);

                for (Player player : Bukkit.getOnlinePlayers())
                    player.sendMessage( ChatColor.GREEN + "Countdown reduced to 30 seconds due to lots of players!");
            }

            if (interval <= 5 && Main.getVoteManager().isVotingActive()) {

                String map = Main.getVoteManager().getWinner();

                for (Player player : Bukkit.getOnlinePlayers())
                    player.sendMessage(ChatColor.GREEN + "Voting has ended! The selected map is " + ChatColor.AQUA + map);

                Main.getMapManager().loadMap(map);
            }

            for (Player player : Bukkit.getOnlinePlayers())
                Title.sendActionbar(player, ChatColor.AQUA + "" + ChatColor.BOLD + "(!) " + ChatColor.GREEN + "Game starting in " + ChatColor.AQUA + interval + "s..."); // ADD 10 so that when they go into cages its seamless.

            if (interval-- <= 0) {
                this.cancel();
                Main.getGameManager().startGame();
            }
        }
    }
}
