package PikaUHC.Config;

import PikaUHC.Main;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.Configuration;
import org.bukkit.plugin.Plugin;

/**
 * Created by Jake
 */
public class ConfigCommand implements CommandExecutor {
    private Plugin plugin;
    private Configuration config = Main.getInstance().getConfig();

    public ConfigCommand(Main instance) {
        this.plugin = instance;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(command.getName().equalsIgnoreCase("config")) {
            if(args.length >= 1) {
                if(args[0].equalsIgnoreCase("set")) {
                    if(args.length == 3) {
                        if(args[1].equalsIgnoreCase("teams-enabled")) {
                            config.set("teams-enabled", Boolean.parseBoolean(args[2].toLowerCase()));
                        } else if(args[1].equalsIgnoreCase("worldborder-size")) {
                            config.set("worldborder", Integer.parseInt(args[2]));
                        } else if(args[1].equalsIgnoreCase("nether")) {
                            config.set("nether-enabled", Boolean.parseBoolean(args[2].toLowerCase()));
                        } else if(args[1].equalsIgnoreCase("strength1-enabled")) {
                            config.set("strength1-enabled", Boolean.parseBoolean(args[2].toLowerCase()));
                        } else if(args[1].equalsIgnoreCase("strength2-enabled")) {
                            config.set("strength2-enabled", Boolean.parseBoolean(args[2].toLowerCase()));
                        } else if(args[1].equalsIgnoreCase("pvptime")) {
                            config.set("pvptime", Integer.parseInt(args[2].toLowerCase()));
                        } else if(args[1].equalsIgnoreCase("teamsize")) {
                            config.set("teamsize", Integer.parseInt(args[2].toLowerCase()));
                        } else if(args[1].equalsIgnoreCase("friendly-fire")) {
                            config.set("friendly-fire", Boolean.parseBoolean(args[2].toLowerCase()));
                        } else if(args[1].equalsIgnoreCase("enderpearldmg")) {
                            config.set("pearl-damage", Boolean.parseBoolean(args[2].toLowerCase()));
                        }
                        saveConfig();
                        return true;
                    }
                }
            } else {
                String teamsenabled = config.getBoolean("teams-enabled") + "";
                teamsenabled = teamsenabled.substring(0,1).toUpperCase() + teamsenabled.substring(1);

                String netherenabled = config.getBoolean("nether-enabled") + "";
                netherenabled = netherenabled.substring(0,1).toUpperCase() + netherenabled.substring(1);

                String strength1enabled = config.getBoolean("strength1-enabled") + "";
                strength1enabled = strength1enabled.substring(0,1).toUpperCase() + strength1enabled.substring(1);

                String strength2enabled = config.getBoolean("strength2-enabled") + "";
                strength2enabled = strength2enabled.substring(0,1).toUpperCase() + strength2enabled.substring(1);

                String friendlyfire = config.getBoolean("friendly-fire") + "";
                friendlyfire = friendlyfire.substring(0,1).toUpperCase() + friendlyfire.substring(1);

                String epearldmg = config.getBoolean("pearl-damage") + "";
                epearldmg = epearldmg.substring(0,1).toUpperCase() + epearldmg.substring(1);

                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cConfig"));
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6Teams: " + teamsenabled + " Teams of " + config.getInt("teamsize")));
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6WorldBorder: " + config.getInt("worldborder-size")));
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6Nether: " + netherenabled));
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6Strength1: " + strength1enabled));
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6Strength2: " + strength2enabled));
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6GoldenHeads: True"));
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6PvPTime: " + config.getInt("pvptime")));
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6Friendly-Fire: " + friendlyfire));
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6EnderPearlDmg: " + epearldmg));
            }
        }
        return false;
    }

    private void saveConfig() {
        Main.getInstance().saveConfig();
    }
}
