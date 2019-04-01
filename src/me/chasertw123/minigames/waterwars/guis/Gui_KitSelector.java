package me.chasertw123.minigames.waterwars.guis;

import me.chasertw123.minigames.core.utils.gui.AbstractGui;
import me.chasertw123.minigames.waterwars.users.User;
import me.chasertw123.minigames.wws.unlocks.kits.Kit;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Sound;

/**
 * Created by Chase on 4/23/2017.
 */
public class Gui_KitSelector extends AbstractGui {

    public Gui_KitSelector(User user) {
        super(((Kit.values().length - 1) / 9) + 1, "Kit Selector | " + user.getSelectedKit().getDisplay(), user.getCoreUser());

        int i = 0;
        for (Kit kit : Kit.values())
            setItem(kit.craftItemStack(user), i++, (S, c, p) -> {

                if (user.getSelectedKit() == kit) {
                    user.getCoreUser().getPlayer().closeInventory();
                    user.getCoreUser().getPlayer().playSound(user.getCoreUser().getPlayer().getLocation(), Sound.NOTE_BASS_DRUM, 1F, 1F);
                    user.getCoreUser().sendMessage(ChatColor.WHITE + "You already have the " + ChatColor.AQUA
                            + kit.getDisplay().toUpperCase() + ChatColor.WHITE + " kit selected!");
                    return;
                }

                if (!user.ownsKit(kit)) {
                    user.getCoreUser().getPlayer().closeInventory();
                    user.getCoreUser().getPlayer().playSound(user.getCoreUser().getPlayer().getLocation(), Sound.NOTE_BASS_DRUM, 1F, 1F);
                    user.getCoreUser().sendMessage(ChatColor.RED + "You don't have the " + ChatColor.AQUA + kit.getDisplay().toUpperCase()
                            + ChatColor.RED + " kit! Buy it in the hub for " + kit.getUnlockCost() + " coins!");
                    return;
                }

                user.getCoreUser().getPlayer().closeInventory();
                user.getCoreUser().getPlayer().playSound(user.getCoreUser().getPlayer().getLocation(), Sound.LEVEL_UP, 1F, 1F);
                user.getCoreUser().sendMessage(ChatColor.WHITE + "You selected the " + ChatColor.AQUA + kit.getDisplay().toUpperCase() + ChatColor.WHITE + " kit!");

                user.setSelectedKit(kit);

            });
    }
}
