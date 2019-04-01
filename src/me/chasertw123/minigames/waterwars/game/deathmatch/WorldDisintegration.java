package me.chasertw123.minigames.waterwars.game.deathmatch;

import me.chasertw123.minigames.waterwars.Main;
import me.chasertw123.minigames.waterwars.game.loops.GameLoop;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;

public class WorldDisintegration extends GameLoop {

    private World world;
    private Location center;
    private int maxRadius, maxY, minY;
    private Random random;

    public WorldDisintegration(World world, Location center, int maxRadius, int minY, int maxY) {
        super(1, 100);

        this.world = world;
        this.center = center;
        this.maxRadius = maxRadius;
        this.random = new Random();
        this.maxY = maxY;
        this.minY = minY;
    }

    @Override
    public void run() {
        // Calculate the radius Async
        Main.newChain()
            .asyncFirst(() -> {

                Map<Integer, List<Location>> locationData = new HashMap<>();
                calculateRadius(center.getBlockX(), center.getBlockZ(), maxRadius, maxRadius - 5).forEach(l -> {

                    for (int y = minY; y < maxY; y++) {

                        int val = random.nextInt(100);
                        if (locationData.containsKey(val)) {
                            List<Location> data = locationData.get(val);
                            data.add(new Location(center.getWorld(), l.getBlockX(), y, l.getBlockZ()));
                            locationData.replace(val, data);
                        }

                        else {
                            List<Location> data = new ArrayList<>();
                            data.add(new Location(center.getWorld(), l.getBlockX(), y, l.getBlockZ()));
                            locationData.put(val, data);
                        }
                    }
                });

                return locationData;
            })
            .syncLast((Map<Integer, List<Location>> locationData) -> new BlockRemover(locationData))
            .execute();

        maxRadius--; // Go one block in
    }

    public int getCurrentRadius() {
        return maxRadius;
    }

    private class BlockRemover extends BukkitRunnable {

        private int currentCount = 0;
        private Map<Integer, List<Location>> locationData;

        BlockRemover(Map<Integer, List<Location>> locationData) {
            this.locationData = locationData;
            this.runTaskTimer(Main.getInstance(), 0L, 1L);
        }

        @Override
        public void run() {

            if (currentCount == 100) {
                this.cancel();
                return;
            }

            for (Map.Entry<Integer, List<Location>> data : locationData.entrySet())
                if (data.getKey() == currentCount)
                    for (Location l : data.getValue())
                        if (!(l.getBlock().getType() == Material.AIR || l.getBlock().getType() == Material.STATIONARY_WATER || l.getBlock().getType() == Material.WATER))
                            l.getBlock().setType(Material.AIR);
                        else if ((l.getBlock().getType() == Material.STATIONARY_WATER || l.getBlock().getType() == Material.WATER) && l.getBlockY() != minY)
                            l.getBlock().setType(Material.AIR);

            currentCount++;
        }
    }

    private List<Location> calculateRadius(int x, int z, int maxRadius, int minRadius) {
        List<Location> ignored = calculateRadius(x, z, minRadius);

        List<Location> accepted = calculateRadius(x, z, maxRadius);
        accepted.removeAll(ignored);

        return accepted;
    }

    private List<Location> calculateRadius(int dx, int dz, int radius) {
        Location tempLocation = new Location(world, dx, 0, dz);
        List<Location> locs = new ArrayList<>();

        for (int x = -(radius); x <= radius; x++)
            for (int z = -(radius); z <= radius; z++)
                locs.add(tempLocation.getBlock().getRelative(x, 0, z).getLocation());

        return locs;
    }

}
