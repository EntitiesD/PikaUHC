package PikaUHC.Game;

import PikaUHC.Main;
import PikaUHC.Teams.Team;
import PikaUHC.UHCPlayer;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.configuration.Configuration;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;

/**
 * Created by Jake
 */
public class Game {
    private GameState state;
    private ArrayList<Team> teams = new ArrayList<Team>();
    private HashMap<String, UHCPlayer> players = new HashMap<String, UHCPlayer>();
    private ArrayList<String> kickedPlayers = new ArrayList<String>();
    private ArrayList<String> spectators = new ArrayList<String>();
    private Integer timerId;
    private Integer seconds = 0;
    private Integer borderIterations = 0;
    private Configuration config = Main.getInstance().getConfig();
    private boolean pvpenabled = false;

    public GameState getState() {
        return this.state;
    }

    public void addPlayer(Player p) {
        players.put(p.getUniqueId().toString(), new UHCPlayer(p));
    }

    public UHCPlayer getPlayer(Player p) {
        return players.get(p.getUniqueId().toString());
    }

    public void start() {
        this.state = GameState.WAITING;
        for(Player p : Bukkit.getOnlinePlayers()) {
            if(!p.hasPermission("uhc.mod")) {
                addPlayer(p);
            }
        }
        timerId = Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.getInstance(), new BukkitRunnable() {
            @Override
            public void run() {
                seconds++;
                if(seconds == 20) {
                    Bukkit.broadcastMessage(ChatColor.GOLD + "10 SECONDS REMAINING");
                }
                if(seconds == 30) {
                    state = GameState.STARTED;
                    teleportPlayers();
                }
                if(players.size() == 1) {
                    for(Map.Entry<String, UHCPlayer> set : players.entrySet()) {
                        String name = Bukkit.getPlayer(UUID.fromString(set.getKey())).getName();
                        Bukkit.broadcastMessage(ChatColor.GOLD + name + " has won the game!");
                    }
                    state = GameState.FINISHED;
                    Bukkit.getScheduler().cancelTask(timerId);
                    end();
                }
                if((seconds / (config.getInt("pvptime") * 60)) == 1 && !pvpenabled) {
                    pvpenabled = true;
                    state = GameState.PVP;
                    for(Map.Entry<String, UHCPlayer> set : players.entrySet()) {
                        Bukkit.getPlayer(UUID.fromString(set.getKey())).sendMessage(ChatColor.translateAlternateColorCodes('&', config.getString("pvp-enabled")));
                    }
                }
                if(seconds > 3600 && (seconds % 300) == 0) {
                    Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', config.getString("worldborder-shrinking")));
                    if((3000 -(500 * borderIterations)) > 500) {
                        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "wb world set " + (config.getInt("worldborder-size") -(500 * borderIterations)) + " 0 0");
                    } else {
                        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "wb world set 100 0 0");
                    }
                    borderIterations++;
                }
            }
        }, 0L, 20L);
    }

    public void addSpectator(Player p) {
        spectators.add(p.getUniqueId().toString());
    }

    public boolean isSpectator(Player p) {
        if(spectators.contains(p.getUniqueId().toString())) {
            return true;
        }
        return false;
    }

    public void removeTeam(Team team) {
        if(teams.contains(team)) {
            teams.remove(team);
        }
    }

    public void kickPlayer(Player p) {
        removePlayer(p);
        p.kickPlayer(ChatColor.RED + "Kicked until the next game!");
        kickedPlayers.add(p.getUniqueId().toString());
    }

    public boolean isPlayerKicked(Player p) {
        if(kickedPlayers.contains(p.getUniqueId().toString())) {
            return true;
        }
        return false;
    }

    public void createTeam(Player p) {
        Team team = new Team(teams.size() + 1);
        teams.add(team);
        team.addMember(players.get(p.getUniqueId().toString()));
    }

    public void end() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            for(Player player1 : Bukkit.getOnlinePlayers()) {
                player.showPlayer(player1);
            }

        }

        this.state = GameState.FINISHED;
        Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getInstance(), new BukkitRunnable() {
            @Override
            public void run() {
                Main.currentGame = null;
            }
        }, 200L);
    }

    public void removePlayer(Player p) {
        for(Team team : teams) {
            team.removeMember(Main.currentGame.getPlayer(p));
        }
        players.remove(p.getUniqueId().toString());
    }

    private void teleportPlayers() {
        Random r = new Random();
        List<UHCPlayer> skip = new ArrayList<UHCPlayer>();
        for(Player p : Bukkit.getOnlinePlayers()) {
            UHCPlayer uhcp = Main.currentGame.getPlayer(p);
            if(skip.contains(uhcp)) {
                break;
            }
            double rangeMax = config.getDouble("worldborder-size");
            double rangeMin = -1 * config.getDouble("worldborder-size");
            double randomValueX = rangeMin + (rangeMax - rangeMin) * r.nextDouble();
            double randomValueY = rangeMin + (rangeMax - rangeMin) * r.nextDouble();
            Location l = new Location(Bukkit.getWorld("world"), randomValueX, 0, randomValueY);
            l.setY(Bukkit.getWorld("world").getHighestBlockYAt(l.getBlockX() + 1, l.getBlockZ()));

            if(uhcp.getTeam() == null) {
                p.teleport(l);
            } else {
                for(UHCPlayer uhcmember : uhcp.getTeam().getMembers()) {
                    Bukkit.getPlayer(UUID.fromString(uhcmember.getUuid())).teleport(l);
                    skip.add(uhcmember);
                }
            }
        }
    }
}
