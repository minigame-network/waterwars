package me.chasertw123.minigames.waterwars.guis;

import me.chasertw123.minigames.core.utils.gui.AbstractGui;
import me.chasertw123.minigames.core.utils.items.cItemStack;
import me.chasertw123.minigames.waterwars.Main;
import me.chasertw123.minigames.waterwars.users.User;

/**
 * Created by Chase on 8/4/2017.
 */
public class Gui_PlayerTeleporter extends AbstractGui {

    public Gui_PlayerTeleporter(User user) {
        super((Main.getGameManager().getInPlayers().size() / 9) + 1, "Who do you want to spectate?", user.getCoreUser());

        int i = 0;
        for (User user1 : Main.getGameManager().getInPlayers())
            setItem(new cItemStack(user1.getPlayer().getName(), user1.getCoreUser().getRank().getRankColor() + user1.getPlayer().getName()), i++,
                    (s, c, p) -> user.getPlayer().teleport(user1.getPlayer().getLocation()));
    }
}
