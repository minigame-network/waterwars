package me.chasertw123.minigames.waterwars.listeners;

import me.chasertw123.minigames.waterwars.Main;
import me.chasertw123.minigames.waterwars.game.auto.AutoCoal;
import me.chasertw123.minigames.waterwars.game.auto.AutoLapis;
import org.bukkit.Bukkit;

/**
 * Created by Scott Hiett on 4/21/2017.
 */
public class EventManager {

    public EventManager(){
        new Event_BlockBreak();
        new Event_BlockCanBuild();
        new Event_BlockPlace();
        new Event_PlayerJoin();
        new Event_ServerPing();
        new Event_PlayerLeave();
        new Event_PlayerInteract();
        new Event_PlayerPickupItem();
        new Event_PlayerDeath();
        new Event_EntityDamage();
        new Event_EntityExplode();
        new Event_InventoryOpen();
        new Event_DamageEntityByEntity();
        new Event_FoodLevel();
        new Event_DropItem();
        new Event_PrepareItemCraft();
        new Event_BlockForm();

        Bukkit.getServer().getPluginManager().registerEvents(new AutoCoal(), Main.getInstance());
        Bukkit.getServer().getPluginManager().registerEvents(new AutoLapis(), Main.getInstance());
    }

}
