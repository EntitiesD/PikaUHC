package PikaUHC.Listeners;

import PikaUHC.Main;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

/**
 * Created by Jake
 */
public class QuitListener implements Listener {
    public QuitListener(Main instance) {
        instance.getServer().getPluginManager().registerEvents(this, instance);
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        e.setQuitMessage(null);
        if(Main.currentGame != null) {
            Main.currentGame.removePlayer(e.getPlayer());
        }
    }
}
