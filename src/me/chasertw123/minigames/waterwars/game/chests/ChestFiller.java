package me.chasertw123.minigames.waterwars.game.chests;

import me.chasertw123.minigames.waterwars.Main;
import me.chasertw123.minigames.waterwars.game.maps.IslandData;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Chest;
import org.bukkit.inventory.Inventory;

import java.util.List;
import java.util.Random;

/**
 * Created by Scott Hiett on 4/21/2017.
 */
public class ChestFiller {

    private Random random;

    private final GameItem[] tier1Items = {
            new GameItem(Material.STONE_SWORD, 1, 100),
            new GameItem(Material.IRON_SWORD, 1, 25),
            new GameItem(Material.LEATHER_HELMET, 1, 100),
            new GameItem(Material.LEATHER_CHESTPLATE, 1, 100),
            new GameItem(Material.LEATHER_LEGGINGS, 1, 100),
            new GameItem(Material.LEATHER_BOOTS, 1, 100),
            new GameItem(Material.IRON_HELMET, 1, 25),
            new GameItem(Material.IRON_CHESTPLATE, 1, 25),
            new GameItem(Material.IRON_LEGGINGS, 1, 25),
            new GameItem(Material.IRON_BOOTS, 1, 25),
            new GameItem(Material.FISHING_ROD, 1, 50),
            new GameItem(Material.FLINT_AND_STEEL, 1, 50),
            new GameItem(Material.WATER_BUCKET, 1, 100),
            new GameItem(Material.LAVA_BUCKET, 1, 50),
            new GameItem(Material.STONE, 16, 100),
            new GameItem(Material.WOOD, 16, 50),
            new GameItem(Material.STONE, 32, 25),
            new GameItem(Material.WOOD, 32, 25),
            new GameItem(Material.COOKED_BEEF, 8, 100),
            new GameItem(Material.GOLDEN_APPLE, 1, 15),
            new GameItem(Material.EXP_BOTTLE, 16, 15),
            new GameItem(Material.STONE_PICKAXE, 1, 50),
            new GameItem(Material.STONE_AXE, 1, 50),
            new GameItem(Material.IRON_AXE, 1, 50)
    };

    private final GameItem[] tier2Items = {
            new GameItem(Material.FLINT_AND_STEEL, 1, 100),
            new GameItem(Material.FLINT_AND_STEEL, 1, 100),
            new GameItem(Material.FISHING_ROD, 1, 100),
            new GameItem(Material.FISHING_ROD, 1, 100),
            new GameItem(Material.WATER_BUCKET, 1, 100),
            new GameItem(Material.WATER_BUCKET, 1, 100),
            new GameItem(Material.WATER_BUCKET, 1, 100),
            new GameItem(Material.WATER_BUCKET, 1, 100),
            new GameItem(Material.LAVA_BUCKET, 1, 100),
            new GameItem(Material.LAVA_BUCKET, 1, 100),
            new GameItem(Material.LAVA_BUCKET, 1, 100),
            new GameItem(Material.LAVA_BUCKET, 1, 100),
            new GameItem(Material.TNT, 8, 100),
            new GameItem(Material.TNT, 8, 50),
            new GameItem(Material.EGG, 8, 100),
            new GameItem(Material.EGG, 16, 50),
            new GameItem(Material.SNOW_BALL, 8, 100),
            new GameItem(Material.SNOW_BALL, 16, 50),
            new GameItem(Material.COOKED_BEEF, 16, 100),
            new GameItem(Material.COOKED_BEEF, 16, 100),
            new GameItem(Material.COOKED_BEEF, 16, 100),
            new GameItem(Material.COOKED_BEEF, 16, 100),
            new GameItem(Material.IRON_AXE, 1, 100),
            new GameItem(Material.IRON_PICKAXE, 1, 100),
            new GameItem(Material.IRON_AXE, 1, 100),
            new GameItem(Material.IRON_PICKAXE, 1, 100)
    };

    private final GameItem[] tier3Items = {
            new GameItem(Material.IRON_SWORD, 1, 100),
            new GameItem(Material.IRON_SWORD, 1, 100),
            new GameItem(Material.COOKED_BEEF, 32, 50),
            new GameItem(Material.EXP_BOTTLE, 32, 50),
            new GameItem(Material.COOKED_BEEF, 32, 50),
            new GameItem(Material.EXP_BOTTLE, 32, 50),
            new GameItem(Material.ENDER_PEARL, 1, 75),
            new GameItem(Material.ENDER_PEARL, 3, 25),
            new GameItem(Material.ENDER_PEARL, 1, 75),
            new GameItem(Material.ENDER_PEARL, 3, 25),
            new GameItem(Material.GOLDEN_APPLE, 1, 75),
            new GameItem(Material.GOLDEN_APPLE, 3, 25),
            new GameItem(Material.GOLDEN_APPLE, 1, 75),
            new GameItem(Material.GOLDEN_APPLE, 3, 25),
            new GameItem(Material.SNOW_BALL, 16, 100),
            new GameItem(Material.SNOW_BALL, 16, 100),
            new GameItem(Material.EGG, 16, 100),
            new GameItem(Material.EGG, 16, 100),
            new GameItem(Material.STONE, 64, 100),
            new GameItem(Material.STONE, 64, 100),
            new GameItem(Material.WOOD, 64, 100),
            new GameItem(Material.WOOD, 64, 100),
            new GameItem(Material.IRON_SWORD, 1, 100),
            new GameItem(Material.DIAMOND_SWORD, 1, 100),
            new GameItem(Material.IRON_AXE, 1, 100),
            new GameItem(Material.DIAMOND_AXE, 1, 100),
            new GameItem(Material.BOW, 1, 100),
            new GameItem(Material.BOW, 1, 100),
            new GameItem(Material.ARROW, 16, 100),
            new GameItem(Material.ARROW, 16, 100),
            new GameItem(Material.DIAMOND_HELMET, 1, 25),
            new GameItem(Material.DIAMOND_CHESTPLATE, 1, 25),
            new GameItem(Material.DIAMOND_LEGGINGS, 1, 25),
            new GameItem(Material.DIAMOND_BOOTS, 1, 25),
            new GameItem(Material.IRON_HELMET, 1, 25),
            new GameItem(Material.IRON_CHESTPLATE, 1, 25),
            new GameItem(Material.IRON_LEGGINGS, 1, 25),
            new GameItem(Material.IRON_BOOTS, 1, 25)
    };

    public ChestFiller(){
        random = new Random();
    }

    public void fillIsland(IslandData islandData){
        for(GameItem g : tier1Items){
            if(getRandomPercent() < g.getChance()){
                int id = random.nextInt(islandData.getChests().size());

                if(islandData.getChests().get(id).getBlock().getType() != Material.CHEST){
                    System.out.println("Block is not chest at " + islandData.getChests().get(id).toString());

                    continue;
                }

                Inventory i = ((Chest) islandData.getChests().get(id).getBlock().getState()).getInventory();
                int slot = random.nextInt(3 * 9);
                if(i.getItem(slot) == null || i.getItem(slot).getType() == Material.AIR){
                    i.setItem(slot, g.getItemStack());
                }else{
                    i.addItem(g.getItemStack());
                }
            }
        }
    }

    public void fill(){
        for(List<Location> islandChest : Main.getGameManager().map.islandsChests()){
            for(GameItem g : tier1Items){
                if(getRandomPercent() < g.getChance()){
                    int id = random.nextInt(islandChest.size());

                    if(islandChest.get(id).getBlock().getType() != Material.CHEST){
                        System.out.println("Block is not chest at " + islandChest.get(id).toString());

                        continue;
                    }

                    Inventory i = ((Chest) islandChest.get(id).getBlock().getState()).getInventory();
                    int slot = random.nextInt(3 * 9);
                    if(i.getItem(slot) == null || i.getItem(slot).getType() == Material.AIR){
                        i.setItem(slot, g.getItemStack());
                    }else{
                        i.addItem(g.getItemStack());
                    }
                }
            }
        }

        List<Location> tier2GC = Main.getGameManager().map.getTier2chests();
        for(GameItem g : tier2Items){
            if(getRandomPercent() < g.getChance()){
                int cid = random.nextInt(tier2GC.size());

                if(tier2GC.get(cid).getBlock().getType() != Material.CHEST){
                    System.out.println("Block is not chest at " + tier2GC.get(cid).toString());

                    continue;
                }

                Location gc = tier2GC.get(cid);
                Chest chest = (Chest) gc.getBlock().getState();
                Inventory i = chest.getInventory();
                int slot = random.nextInt(3 * 9);
                if(i.getItem(slot) == null || i.getItem(slot).getType() == Material.AIR){
                    i.setItem(slot, g.getItemStack());
                }else{
                    i.addItem(g.getItemStack());
                }
            }
        }

        List<Location> tier3GC = Main.getGameManager().map.getTier3chests();
        for(GameItem g : tier3Items){
            if(getRandomPercent() < g.getChance()){
                int cid = random.nextInt(tier3GC.size());

                if(tier3GC.get(cid).getBlock().getType() != Material.CHEST){
                    System.out.println("Block is not chest at " + tier3GC.get(cid).toString());

                    continue;
                }

                Location gc = tier3GC.get(cid);
                Chest chest = (Chest) gc.getBlock().getState();
                Inventory i = chest.getInventory();
                int slot = random.nextInt(3 * 9);
                if(i.getItem(slot) == null || i.getItem(slot).getType() == Material.AIR){
                    i.setItem(slot, g.getItemStack());
                }else{
                    i.addItem(g.getItemStack());
                }
            }
        }
    }

    private int getRandomPercent(){
        return random.nextInt(100) + 1;
    }

}
