package me.chasertw123.minigames.waterwars.guis;

import me.chasertw123.minigames.core.utils.gui.AbstractGui;
import me.chasertw123.minigames.waterwars.unlocks.perks.PerkType;
import me.chasertw123.minigames.waterwars.users.User;

/**
 * Created by Chase on 5/2/2017.
 */
public class Gui_Perks extends AbstractGui {

    public Gui_Perks(User user) {
        super((PerkType.values().length / 9) + 1, "Your Perks", user.getCoreUser());

        int i = 0;
        for (PerkType perkType : PerkType.values())
            setItem(perkType.getPerkClass().getDisplay(user), i++, (s, c, p) -> {}); //TODO: Set actions when you click
    }
}
