package PikaUHC.Listeners;

import PikaUHC.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

/**
 * Created by Jake
 */
public class LoginListener implements Listener {

    public LoginListener(Main instance) {
        instance.getServer().getPluginManager().registerEvents(this, instance);
    }

    @EventHandler
    public void onPlayerLogin(PlayerJoinEvent e) {
        e.setJoinMessage(null);
        e.getPlayer().setAllowFlight(false);
        for(Player player : Bukkit.getOnlinePlayers()) {
            player.showPlayer(e.getPlayer());
        }
        if(Main.currentGame != null && !e.getPlayer().hasPermission("uhc.mod")) {
            e.getPlayer().kickPlayer(ChatColor.RED + "You are banned until the next game!");
        }
    }
}
