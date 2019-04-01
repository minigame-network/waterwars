package me.chasertw123.minigames.waterwars.guis;

import me.chasertw123.minigames.core.utils.gui.AbstractGui;
import me.chasertw123.minigames.waterwars.unlocks.cages.CageManager;
import me.chasertw123.minigames.waterwars.users.User;
import me.chasertw123.minigames.wws.unlocks.cages.Cage;
import org.bukkit.ChatColor;
import org.bukkit.Sound;

/**
 * Created by Chase on 4/23/2017.
 */
public class Gui_CageSelector extends AbstractGui {

    public Gui_CageSelector(User user) {
        super(((Cage.values().length - 1) / 9 )+ 1, "Cage Selector | " + user.getSelectedCage().getDisplay(), user.getCoreUser());

        int i = 0;
        for (Cage cage : Cage.values())
            setItem(cage.getGameItemStack(user), i++, (s, c, p) -> {

                if (user.getSelectedCage() == cage) {
                    user.getCoreUser().getPlayer().closeInventory();
                    user.getCoreUser().getPlayer().playSound(user.getCoreUser().getPlayer().getLocation(), Sound.NOTE_BASS_DRUM, 1F, 1F);
                    user.getCoreUser().getPlayer().sendMessage(ChatColor.WHITE + "You already have the " + ChatColor.AQUA
                            + cage.getDisplay().toUpperCase() + ChatColor.WHITE + " cage selected!");
                    return;
                }

                if (!user.ownsCage(cage)) {
                    user.getCoreUser().getPlayer().playSound(user.getCoreUser().getPlayer().getLocation(), Sound.NOTE_BASS_DRUM, 1F, 1F);
                    return;
                }

                user.getCoreUser().getPlayer().closeInventory();
                user.getCoreUser().getPlayer().playSound(user.getCoreUser().getPlayer().getLocation(), Sound.FIREWORK_LARGE_BLAST, 1F, 1F);
                user.getCoreUser().getPlayer().sendMessage(ChatColor.WHITE + "You selected the " + ChatColor.AQUA
                        + cage.getDisplay().toUpperCase() + ChatColor.WHITE + " cage!");

                user.setCageType(cage);
            });
    }
}
