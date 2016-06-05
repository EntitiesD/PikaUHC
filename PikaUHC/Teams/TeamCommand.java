package PikaUHC.Teams;

import PikaUHC.Game.GameState;
import PikaUHC.Main;
import PikaUHC.UHCPlayer;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.HashMap;
import java.util.UUID;

/**
 * Created by Jake
 */
public class TeamCommand implements CommandExecutor {
    private Plugin plugin;
    private static HashMap<String, Team> invites = new HashMap<String, Team>();

    public TeamCommand(Main instance) {
        this.plugin = instance;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(command.getName().equalsIgnoreCase("team")) {
            if(sender instanceof Player) {
                if(!plugin.getConfig().getBoolean("teams-enabled")) {
                    sender.sendMessage(ChatColor.RED + "Teams are not currently enabled");
                    return true;
                }
                Player p = (Player) sender;
                if(!p.hasPermission("uhc.team")) {
                    p.sendMessage(ChatColor.RED + "No permission.");
                    return true;
                }
                if(args.length >= 1) {
                    if(args[0].equalsIgnoreCase("list")) {
                        UHCPlayer uhcPlayer = Main.currentGame.getPlayer(p);
                        if(uhcPlayer.getTeam() != null) {
                            p.sendMessage(ChatColor.GREEN + "Your current team: " + uhcPlayer.getTeam().getTeamNumber());
                            for(UHCPlayer teamMember : uhcPlayer.getTeam().getMembers()) {
                                p.sendMessage(ChatColor.AQUA + Bukkit.getPlayer(UUID.fromString(teamMember.getUuid())).getName());
                            }
                            return true;
                        } else {
                            p.sendMessage(ChatColor.RED + "You are not in a team!");
                            return true;
                        }

                    }
                    if(Main.currentGame.getState() != GameState.WAITING) {
                        return true;
                    }
                    if(args[0].equalsIgnoreCase("invite")) {
                        if(args.length == 2) {
                            if(Bukkit.getPlayer(args[1]) != null) {
                                Player target = Bukkit.getPlayer(args[1]);
                                target.sendMessage(ChatColor.GREEN + "You have been invited to team with " + p.getName());
                                UHCPlayer uhcPlayer = Main.currentGame.getPlayer(p);
                                if(uhcPlayer.getTeam() == null) {
                                    Main.currentGame.createTeam(p);
                                }
                                invites.put(target.getUniqueId().toString(), Main.currentGame.getPlayer(p).getTeam());
                            }
                        }
                    } else if(args[0].equalsIgnoreCase("accept")) {
                        if(invites.containsKey(p.getUniqueId().toString())) {
                            Team team = invites.get(p.getUniqueId().toString());
                            team.addMember(Main.currentGame.getPlayer(p));
                            invites.remove(p.getUniqueId().toString());
                            p.sendMessage(ChatColor.GREEN + "You have joined a new team!");
                            return true;
                        } else {
                            p.sendMessage(ChatColor.RED + "You have no invites!");
                            return true;
                        }
                    } else if(args[0].equalsIgnoreCase("decline")) {
                        if(invites.containsKey(p.getUniqueId().toString())) {
                            invites.remove(p.getUniqueId().toString());
                            p.sendMessage(ChatColor.GREEN + "You have declined an invite!");
                            return true;
                        } else {
                            p.sendMessage(ChatColor.RED + "You have no invites!");
                            return true;
                        }
                    } else if(args[0].equalsIgnoreCase("kick")) {
                        if(args.length == 2) {
                            if(Bukkit.getPlayer(args[1]) != null) {
                                Player target = Bukkit.getPlayer(args[1]);
                                if(Main.currentGame.getPlayer(p).getTeam().getMembers().contains(Main.currentGame.getPlayer(target))) {
                                    Main.currentGame.getPlayer(p).getTeam().removeMember(Main.currentGame.getPlayer(target));
                                    p.sendMessage(ChatColor.GREEN + "You successfully kicked " + args[1]);
                                    return true;
                                } else {
                                    p.sendMessage(ChatColor.RED + "That player is not in your team!");
                                    return true;
                                }
                            }
                        }
                    } else if(args[0].equalsIgnoreCase("leave")) {
                        Main.currentGame.getPlayer(p).getTeam().removeMember(Main.currentGame.getPlayer(p));
                        return true;
                    }
                }
            }

        }
        return false;
    }
}
