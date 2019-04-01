package me.chasertw123.minigames.waterwars.game.scoreboards;

import me.chasertw123.minigames.core.utils.scoreboard.Entry;
import me.chasertw123.minigames.core.utils.scoreboard.EntryBuilder;
import me.chasertw123.minigames.core.utils.scoreboard.ScoreboardHandler;
import me.chasertw123.minigames.core.utils.scoreboard.SimpleScoreboard;
import me.chasertw123.minigames.waterwars.Main;
import me.chasertw123.minigames.waterwars.game.GameManager;
import me.chasertw123.minigames.waterwars.game.maps.BaseMap;
import me.chasertw123.minigames.waterwars.users.User;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.List;

public class SB_Lobby extends SimpleScoreboard {

    public SB_Lobby(User user) {
        super(user.getPlayer());

        this.setUpdateInterval(2L);
        this.setHandler(new ScoreboardHandler() {

            @Override
            public String getTitle(Player player) {
                return ChatColor.AQUA + "" + ChatColor.BOLD + "Water" + ChatColor.BLUE + "" + ChatColor.BOLD + "Wars";
            }

            @Override
            public List<Entry> getEntries(Player player) {

                EntryBuilder eb = new EntryBuilder();

                eb.blank();
                eb.next(ChatColor.BLUE + "Players: " + ChatColor.WHITE + Bukkit.getOnlinePlayers().size() + "/" + GameManager.MAX_PLAYERS);
                eb.blank();
                eb.next(ChatColor.GREEN + "Kit: " + ChatColor.WHITE + user.getSelectedKit().getDisplay());
                eb.next(ChatColor.GREEN + "Cage: " + ChatColor.WHITE + user.getSelectedCage().getDisplay());
                eb.blank();
                eb.next(ChatColor.YELLOW + "Map Votes");

                for (BaseMap map : Main.getMapManager().getMaps())
                    eb.next(ChatColor.WHITE + map.getName() + " " + ChatColor.GREEN + "(" + Main.getVoteManager().getVotes(map) + ")");

                eb.blank();

                return eb.build();
            }
        });
    }
}
