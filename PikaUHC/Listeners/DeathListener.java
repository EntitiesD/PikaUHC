package PikaUHC.Listeners;

import PikaUHC.Main;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.BlockState;
import org.bukkit.block.Skull;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.scheduler.BukkitRunnable;

/**
 * Created by Jake
 */
public class DeathListener implements Listener {
    public DeathListener(Main instance) {
        instance.getServer().getPluginManager().registerEvents(this, instance);
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        final Player deadPlayer = event.getEntity();



        Location fenceLocation = deadPlayer.getLocation();
        Location skullLocation = deadPlayer.getLocation();

        skullLocation.setY(skullLocation.getY()+1);


        skullLocation.getBlock().setType(Material.SKULL);

        fenceLocation.getBlock().setType(Material.FENCE);

        Block block = skullLocation.getBlock();

        block.setData((byte)0x1);
        BlockState state = block.getState();

        if(state instanceof Skull)
        {
            Skull skull = (Skull)state;
            skull.setRotation(BlockFace.NORTH);
            skull.setSkullType(SkullType.PLAYER);
            skull.setOwner(deadPlayer.getName());
            skull.update();
        }
        deadPlayer.sendMessage(ChatColor.translateAlternateColorCodes('&', Main.getInstance().getConfig().getString("death-message")));
        if(!deadPlayer.hasPermission("uhc.spectate")) {
            Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getInstance(), new BukkitRunnable() {
                @Override
                public void run() {
                    Main.currentGame.kickPlayer(deadPlayer);
                }
            }, 600L);
        } else {
            Main.currentGame.addSpectator(deadPlayer);

        }
    }
}
