package me.chasertw123.minigames.waterwars.game.loops;

import me.chasertw123.minigames.core.user.data.stats.Stat;
import me.chasertw123.minigames.core.utils.items.cItemStack;
import me.chasertw123.minigames.shared.framework.ServerGameType;
import me.chasertw123.minigames.shared.framework.ServerSetting;
import me.chasertw123.minigames.waterwars.Main;
import me.chasertw123.minigames.waterwars.users.User;
import net.minecraft.server.v1_8_R3.EntityFireworks;
import net.minecraft.server.v1_8_R3.ItemStack;
import net.minecraft.server.v1_8_R3.Items;
import org.bukkit.*;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Random;

/**
 * Created by Chase on 8/4/2017.
 */
public class Loop_GameOver extends GameLoop {

    private Random rand = new Random();
    private int radius = 32;

    public Loop_GameOver(User winner) {
        super(1, 20);

        winner.getCoreUser().addActivity("1st Water Wars (solo)");
        winner.getCoreUser().incrementStat(Stat.WATER_WARS_PLAYTIME, (int) (System.currentTimeMillis() - winner.getJoinTime()) / 1000);
        winner.getCoreUser().incrementStat(Stat.WATER_WARS_SOLO_GAMES_WON);
        winner.getCoreUser().giveCoins(ServerGameType.WATER_WARS, 50, false);
        winner.getCoreUser().giveExperience(50);

        winner.setDead(true);
        winner.getPlayer().getInventory().setArmorContents(null);
        winner.getPlayer().getInventory().clear();
        winner.getPlayer().setLevel(0);
        winner.getPlayer().setExp(0);
        winner.getPlayer().setFoodLevel(20);
        winner.getPlayer().setHealth(winner.getPlayer().getMaxHealth());
        winner.getPlayer().setSaturation(14.4F);
        winner.getPlayer().setGameMode(GameMode.ADVENTURE);
        winner.getPlayer().setAllowFlight(true);
        winner.getPlayer().setFlying(true);

        for (Player player : Bukkit.getOnlinePlayers()) {
            player.playSound(player.getLocation(), Sound.ENDERDRAGON_GROWL, 0.75F, 1F);

            for (Player player1 : Bukkit.getOnlinePlayers())
                if (!player.getUniqueId().equals(player1.getUniqueId())) {
                    player1.showPlayer(player);
                    player.showPlayer(player1);
                }
        }

        winner.getPlayer().getInventory().setItem(0, new cItemStack(Material.COMPASS, ChatColor.AQUA + "Player Teleporter " + ChatColor.GRAY + "(Right Click)"));
        winner.getPlayer().getInventory().setItem(7, new cItemStack(Material.PAPER, ChatColor.GREEN + "Play Again " + ChatColor.GRAY + "(Right Click)"));
        winner.getPlayer().getInventory().setItem(8, new cItemStack(Material.BED, ChatColor.RED + "Return to Hub " + ChatColor.GRAY + "(Right Click)"));

        for (Player player : Bukkit.getOnlinePlayers()) {
            User user = Main.getUserManager().get(player.getUniqueId());

            user.getCoreUser().setServerSetting(ServerSetting.DAMAGE, false);
            user.getCoreUser().setServerSetting(ServerSetting.HUNGER, false);
            user.getCoreUser().setServerSetting(ServerSetting.ITEM_DROPPING, false);

            user.getCoreUser().sendCenteredMessage("&a&m-------------------------------------", true);
            user.getCoreUser().sendCenteredMessage(" ", true);
            user.getCoreUser().sendCenteredMessage("&c&lGAME OVER! &e" + winner.getPlayer().getName() + " WINS!", true);
            user.getCoreUser().sendCenteredMessage("", true);
            user.getCoreUser().sendCenteredMessage("&aYou earned a total of " + user.getCoreUser().getGainedCoins() + " coins!", true);
            user.getCoreUser().sendCenteredMessage("&bYou earned a total of " + user.getCoreUser().getGainedXp() + " experience!", true);
            user.getCoreUser().sendCenteredMessage("", true);
            user.getCoreUser().sendCenteredMessage("&a&m-------------------------------------", true);

            Bukkit.getServer().getScheduler().runTaskLater(Main.getInstance(), () -> {
                user.getPlayer().sendMessage( ChatColor.RED + "Game Over " + ChatColor.GRAY + ">> " + ChatColor.DARK_AQUA + "" + ChatColor.BOLD + "Going to hub in 10 seconds.");
                if (user.getCoreUser().getRank().getRankType().getRankLevel() == 0) {
                    user.getCoreUser().sendCenteredMessage("&d&m-------------------------------------", true);
                    user.getCoreUser().sendCenteredMessage("&9" + user.getPlayer().getName() + "! &6Thanks for playing PVPCentral!", true);
                    user.getCoreUser().sendCenteredMessage("", true);
                    user.getCoreUser().sendCenteredMessage("&f&lWe noticed you don't have a rank!", true);
                    user.getCoreUser().sendCenteredMessage("&7Buy today to &cjoin priority queue, &7get a &ecolored name,", true);
                    user.getCoreUser().sendCenteredMessage("&bmore friend slots, &7and &aeven more!", true);
                    user.getCoreUser().sendCenteredMessage("", true);
                    user.getCoreUser().sendCenteredMessage("&a&l >> store.pvpcentral.net <<", true);
                    user.getCoreUser().sendCenteredMessage("&d&m-------------------------------------", true);
                }
            }, 100);
        }
    }

    @Override
    public void run() {

        for (Player player : Bukkit.getOnlinePlayers()) {

            Location center = player.getLocation(), newLoc;
            double angle = rand.nextDouble() * 360;
            double x = center.getX() + (rand.nextDouble() * radius * Math.cos(Math.toRadians(angle)));
            double z = center.getZ() + (rand.nextDouble() * radius * Math.sin(Math.toRadians(angle)));
            newLoc = new Location(center.getWorld(), x, center.getY() + 2, z);

            playFirework(newLoc);
        }

    }

    private void playFirework(Location l) {

        FireworkEffect.Builder fwB = FireworkEffect.builder();
        Random r = new Random();
        fwB.flicker(r.nextBoolean());
        fwB.trail(r.nextBoolean());
        fwB.with(FireworkEffect.Type.values()[r.nextInt(FireworkEffect.Type.values().length)]);
        fwB.withColor(Color.fromRGB(r.nextInt(256), r.nextInt(256), r.nextInt(256)));
        fwB.withFade(Color.fromRGB(r.nextInt(256), r.nextInt(256), r.nextInt(256)));
        FireworkEffect fe = fwB.build();

        ItemStack item = new ItemStack(Items.FIREWORKS);


        FireworkMeta meta = (FireworkMeta) CraftItemStack.asCraftMirror(item).getItemMeta();
        meta.addEffect(fe);

        CraftItemStack.setItemMeta(item, meta);

        new BukkitRunnable() {

            @Override
            public void run() {

                EntityFireworks entity = new EntityFireworks(((CraftWorld) l.getWorld()).getHandle(), l.getX(), l.getY(), l.getZ(), item) {
                    @Override
                    public void t_() {
                        this.world.broadcastEntityEffect(this, (byte)17);
                        die();
                    }
                };

                entity.setInvisible(true);
                ((CraftWorld) l.getWorld()).getHandle().addEntity(entity);
            }
        }.runTaskLater(Main.getInstance(), 2);
    }
}
