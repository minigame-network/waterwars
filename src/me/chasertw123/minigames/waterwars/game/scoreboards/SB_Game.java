package me.chasertw123.minigames.waterwars.game.scoreboards;

import me.chasertw123.minigames.core.utils.scoreboard.Entry;
import me.chasertw123.minigames.core.utils.scoreboard.EntryBuilder;
import me.chasertw123.minigames.core.utils.scoreboard.ScoreboardHandler;
import me.chasertw123.minigames.core.utils.scoreboard.SimpleScoreboard;
import me.chasertw123.minigames.waterwars.Main;
import me.chasertw123.minigames.waterwars.users.User;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.List;

public class SB_Game extends SimpleScoreboard {

    public SB_Game(User user) {
        super(user.getPlayer());

        this.setUpdateInterval(2L);
        this.setHandler(new ScoreboardHandler() {

            @Override
            public String getTitle(Player player) {

                int time = Main.getGameManager().gameLoop.interval, minutes = time / 60, seconds = time % 60;
                String mins = (minutes < 10 ? "0" : "") + minutes, secs = (seconds < 10 ? "0" : "") + seconds;

                return ChatColor.AQUA + "" + ChatColor.BOLD + "Water" + ChatColor.BLUE + "" + ChatColor.BOLD + "Wars "
                        + ChatColor.GREEN + (Main.getGameManager().worldDisintegration != null ? "[DM]" :  mins + ":" + secs);
            }

            @Override
            public List<Entry> getEntries(Player player) {

                EntryBuilder eb = new EntryBuilder();

                eb.blank();
                eb.next(ChatColor.YELLOW + "" + ChatColor.BOLD + "Game Info");
                eb.next(ChatColor.WHITE + "Player Left: " + ChatColor.GREEN + Main.getGameManager().getAliveUsers().size());
                eb.blank();
                eb.next(ChatColor.GREEN + "" + ChatColor.BOLD + "My Info");
                eb.next(ChatColor.WHITE + "Kills: " + ChatColor.GREEN + user.getKills());
                eb.next(ChatColor.WHITE + "Lives: " + ChatColor.GREEN + user.getLives());
                eb.blank();

                return eb.build();
            }
        });
    }
}
