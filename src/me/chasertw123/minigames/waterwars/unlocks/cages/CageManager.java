package me.chasertw123.minigames.waterwars.unlocks.cages;

import me.chasertw123.minigames.waterwars.Main;
import me.chasertw123.minigames.wws.unlocks.cages.Cage;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.UUID;

/**
 * Created by Scott Hiett on 4/21/2017.
 */
public class CageManager {

    public HashMap<UUID, Location> cageBuildsLocs;

    public CageManager(){
        cageBuildsLocs = new HashMap<>();
    }

    public void buildCage(Player p, Location l){
        Main.getUserManager().get(p.getUniqueId()).getSelectedCage().build(l);
        cageBuildsLocs.put(p.getUniqueId(), l.clone()); // Keep the location, as if the player jumps may remove the cage on wrong y axis
    }

    public void removeCage(Player player) {
        for (Location l : Cage.getAllCageBlockLocations(cageBuildsLocs.get(player.getUniqueId())))
            l.getBlock().setType(Material.AIR);
    }

    public void removePlayersCages(){
        for (Player player : Bukkit.getServer().getOnlinePlayers())
            for (Location l : Cage.getAllCageBlockLocations(cageBuildsLocs.get(player.getUniqueId())))
                l.getBlock().setType(Material.AIR);
    }

}
