package PikaUHC.Listeners;

import PikaUHC.Game.GameState;
import PikaUHC.Main;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

/**
 * Created by Jake
 */
public class InteractListener implements Listener {
    public InteractListener(Main instance) {
        instance.getServer().getPluginManager().registerEvents(this, instance);
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent e) {
        if(Main.currentGame != null) {
            if(Main.currentGame.isSpectator(e.getPlayer())) {
                e.setCancelled(true);
            }
            if(Main.currentGame.getState() != GameState.PVP) {
                if(e.getItem() != null) {
                    if(e.getItem().getType() == Material.LAVA_BUCKET || e.getItem().getType() == Material.FLINT_AND_STEEL || e.getItem().getType() == Material.FIREBALL) {
                        e.setCancelled(true);
                    }
                }
            }
        }
    }
}
