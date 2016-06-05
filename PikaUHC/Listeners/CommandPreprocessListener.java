package PikaUHC.Listeners;

import PikaUHC.Main;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

/**
 * Created by Jake
 */
public class CommandPreprocessListener implements Listener {
    public CommandPreprocessListener(Main instance) {
        instance.getServer().getPluginManager().registerEvents(this, instance);
    }

    @EventHandler
    public void onCommandProcess(PlayerCommandPreprocessEvent e) {
        if(e.getMessage().replaceAll("/", "").split(" ")[0].equalsIgnoreCase("tele")) {
            if(!Main.currentGame.isSpectator(e.getPlayer())) {
                e.setCancelled(true);
            }
        }
    }
}
