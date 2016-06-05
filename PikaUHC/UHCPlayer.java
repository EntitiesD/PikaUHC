package PikaUHC;

import PikaUHC.Teams.Team;
import org.bukkit.entity.Player;

import java.util.ArrayList;

/**
 * Created by Jake
 */
public class UHCPlayer {
    private static ArrayList<UHCPlayer> uhcplayers = new ArrayList<UHCPlayer>();
    private String uuid;
    private Team team = null;

    public UHCPlayer(Player p) {
        this.uuid = p.getUniqueId().toString();
        uhcplayers.add(this);
    }

    public Team getTeam() {
        return team;
    }

    public String getUuid() {
        return uuid;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public static void removePlayer(Player p) {
        for(UHCPlayer uhcp : uhcplayers) {
            if(uhcp.getUuid() == p.getUniqueId().toString()) {
                uhcplayers.remove(uhcp);
                return;
            }
        }
    }
}
