package me.chasertw123.minigames.waterwars.game.maps;

import me.chasertw123.minigames.waterwars.Main;
import org.bukkit.Location;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Scott Hiett on 7/31/2017.
 */
public class GameMap extends BaseMap {

    private Location mapCenter;
    private int maxRadius, maxY, waterY;
    private List<Location> tier2chests, tier3chests;

    private List<IslandData> islandData;

    public GameMap(String map){
        super(map);

        this.maxRadius = getConfigFile().getInt("map.max-radius");
        this.maxY = getConfigFile().getInt("map.max-y");
        this.waterY = getConfigFile().getInt("map.water-y");

        this.mapCenter = getLocation(getConfigFile().getString("map.center"));

        this.tier2chests = getLocationList(getConfigFile().getStringList("tierchests.2"));
        this.tier3chests = getLocationList(getConfigFile().getStringList("tierchests.3"));

        this.islandData = loadIslandData();
    }

    public List<List<Location>> islandsChests(){
        List<List<Location>> data = new ArrayList<>();

        for(IslandData id : islandData)
            data.add(id.getChests());

        return data;
    }

    public Location getMapCenter() {
        return mapCenter;
    }

    public int getMaxRadius() {
        return maxRadius;
    }

    public int getMaxY() {
        return maxY;
    }

    public int getWaterY() {
        return waterY;
    }

    public List<Location> getTier2chests() {
        return tier2chests;
    }

    public List<Location> getTier3chests() {
        return tier3chests;
    }

    private List<IslandData> loadIslandData(){
        List<IslandData> data = new ArrayList<>();

        for(int i=0; i<12; i++)
            data.add(new IslandData(getLocationList(getConfigFile().getStringList("islands." + (i + 1) + ".spawns")),
                    getLocationList(getConfigFile().getStringList("islands." + (i + 1) + ".chests"))));

        return data;
    }

    public IslandData getIslandData(int island){
        return islandData.get(island);
    }

    private List<Location> getLocationList(List<String> values){
        List<Location> data = new ArrayList<>();

        for(String s : values)
            data.add(getLocation(s));

        return data;
    }

    private Location getLocation(String target){
        String[] parts = target.split(",");

        return new Location(Main.getMapManager().getGameWorld(), Integer.parseInt(parts[0].replaceAll(" ", "")),
                Integer.parseInt(parts[1].replaceAll(" ", "")), Integer.parseInt(parts[2].replaceAll(" ", "")));
    }
}
