package me.chasertw123.minigames.waterwars.game.chests;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

/**
 * Created by Scott Hiett on 4/21/2017.
 */
public class GameItem {

    private ItemStack stack;
    private int chance;

    public GameItem(Material m, int amount, int chance){
        stack = new ItemStack(m, amount);
        this.chance = chance;
    }

    public GameItem(Material m, int amount, int data, int chance){
        stack = new ItemStack(m, amount, (short) data);
        this.chance = chance;
    }

    public ItemStack getItemStack(){
        return this.stack;
    }

    public int getChance(){
        return this.chance;
    }

}
