package PikaUHC.Listeners;

import PikaUHC.Game.GameState;
import PikaUHC.Main;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

/**
 * Created by Jake
 */
public class MoveListener implements Listener {
    public MoveListener(Main instance) {
        instance.getServer().getPluginManager().registerEvents(this, instance);
    }

    @EventHandler
    public void onMove(PlayerMoveEvent e) {
        if(Main.currentGame != null) {
            if(Main.currentGame.getState() == GameState.WAITING) {
                e.setCancelled(true);
            }
        }
    }
}
