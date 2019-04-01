package me.chasertw123.minigames.waterwars.game.maps;

import org.bukkit.Location;

import java.util.List;
import java.util.Random;

/**
 * Created by Scott Hiett on 7/31/2017.
 */
public class IslandData {

    private Location spawn;
    private List<Location> chests;

    public IslandData (List<Location> spawns, List<Location> chests){
        if(spawns.size() > 1)
            spawn = spawns.get(new Random().nextInt(spawns.size()));
        else
            spawn = spawns.get(0);

        this.chests = chests;
    }

    public List<Location> getChests() {
        return chests;
    }

    public Location getSpawn() {
        return spawn;
    }

}
