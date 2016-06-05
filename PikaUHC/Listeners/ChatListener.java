package PikaUHC.Listeners;

import PikaUHC.Main;
import PikaUHC.Teams.Team;
import PikaUHC.UHCPlayer;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

/**
 * Created by Jake
 */
public class ChatListener implements Listener {
    public ChatListener(Main instance) {
        instance.getServer().getPluginManager().registerEvents(this, instance);
    }
    
    @EventHandler
    public void onChat(AsyncPlayerChatEvent e) {
        if(Main.currentGame.getPlayer(e.getPlayer()) != null) {
            UHCPlayer uhcPlayer = Main.currentGame.getPlayer(e.getPlayer());
            if(uhcPlayer.getTeam() != null) {
                Team team = uhcPlayer.getTeam();
                e.setFormat(ChatColor.translateAlternateColorCodes('&', "&" + team.getColor()) + "[Team " + team.getTeamNumber() + "] " + ChatColor.RESET + e.getFormat());
            }
        }
    }
}
