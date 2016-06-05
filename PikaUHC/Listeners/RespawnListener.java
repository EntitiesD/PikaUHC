package PikaUHC.Listeners;

import PikaUHC.Main;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

/**
 * Created by Jake
 */
public class RespawnListener implements Listener {
    public RespawnListener(Main instance) {
        instance.getServer().getPluginManager().registerEvents(this, instance);
    }

    @EventHandler
    public void onRespawn(final PlayerRespawnEvent e) {
        if(Main.currentGame.isSpectator(e.getPlayer())) {
            Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getInstance(), new BukkitRunnable() {
                @Override
                public void run() {
                    for(Player player : Bukkit.getOnlinePlayers()) {
                        player.hidePlayer(e.getPlayer());
                    }
                    e.getPlayer().getInventory().setItem(4, new ItemStack(Material.COMPASS, 1));

                }
            }, 2L);
            e.getPlayer().setAllowFlight(true);
            e.getPlayer().setFlying(true);
        }
    }

    @EventHandler
    public void onPickup(PlayerPickupItemEvent e) {
        if(Main.currentGame.isSpectator(e.getPlayer())) {
            e.setCancelled(true);
        }
    }
}
