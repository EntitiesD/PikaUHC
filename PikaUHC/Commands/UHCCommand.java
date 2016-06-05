package PikaUHC.Commands;

import PikaUHC.Game.Game;
import PikaUHC.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;

/**
 * Created by Jake
 */
public class UHCCommand implements CommandExecutor {
    private Plugin plugin;

    public UHCCommand(Main instance) {
        this.plugin = instance;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(command.getName().equalsIgnoreCase("uhc")) {
            if(args.length >= 1) {
                if(args[0].equalsIgnoreCase("start")) {
                    if(Main.currentGame != null) {
                        sender.sendMessage(ChatColor.RED + "A game is already running!");
                        return true;
                    }
                    Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("game-start")));
                    Main.currentGame = new Game();
                    Main.currentGame.start();
                    sender.sendMessage(ChatColor.GREEN + "Game started!");
                } else if(args[0].equalsIgnoreCase("stop")) {
                    if(Main.currentGame == null) {
                        sender.sendMessage("No game is running!");
                        return true;
                    }
                    Main.currentGame.end();
                } else if(args[0].equalsIgnoreCase("deathban") && sender.hasPermission("uhc.deathban")) {
                    Main.currentGame.kickPlayer(Bukkit.getPlayer(args[1]));
                    return true;
                }
            }
        }
        return false;
    }
}
