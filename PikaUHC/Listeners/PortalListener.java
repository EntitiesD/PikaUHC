package PikaUHC.Listeners;

import PikaUHC.Main;
import org.bukkit.World;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerPortalEvent;

/**
 * Created by Jake
 */
public class PortalListener implements Listener {
    public PortalListener(Main instance) {
        instance.getServer().getPluginManager().registerEvents(this, instance);
    }

    @EventHandler
    public void onPortal(PlayerPortalEvent e) {
        if(e.getTo().getWorld().getEnvironment() == World.Environment.NETHER) {
            if(!Main.getInstance().getConfig().getBoolean("nether-enabled")) {
                e.setCancelled(true);
            }
        }
    }
}
