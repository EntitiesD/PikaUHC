package PikaUHC.Teams;

import PikaUHC.Main;
import PikaUHC.UHCPlayer;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

/**
 * Created by Jake
 */
public class Team {
    private ArrayList<UHCPlayer> members = new ArrayList<UHCPlayer>();
    private Integer teamNumber;
    private String color;
    private static List<String> colorOptions = new ArrayList<String>() {{
        add("0");
        add("1");
        add("2");
        add("3");
        add("4");
        add("5");
        add("6");
        add("7");
        add("8");
        add("9");
        add("a");
        add("b");
        add("c");
        add("d");
        add("e");
        add("f");
    }};

    public Team(Integer teamNumber) {
        this.teamNumber = teamNumber;
        Random r = new Random();
        this.color = colorOptions.get(r.nextInt(colorOptions.size() - 1));
    }

    public String getColor() {
        return color;
    }

    public void addMember(UHCPlayer uhcPlayer) {
        if(members.size() + 1 > Main.getInstance().getConfig().getInt("teamsize")) {
            Bukkit.getPlayer(UUID.fromString(uhcPlayer.getUuid())).sendMessage(ChatColor.RED + "You can't join that team, it has too many players!");
            return;
        }
        for(UHCPlayer player : members) {
            Bukkit.getPlayer(UUID.fromString(player.getUuid())).sendMessage(ChatColor.AQUA + Bukkit.getPlayer(UUID.fromString(uhcPlayer.getUuid())).getName() + " has joined your team!");
        }
        members.add(uhcPlayer);
        uhcPlayer.setTeam(this);
    }

    public int getTeamNumber() {
        return teamNumber;
    }

    public ArrayList<UHCPlayer> getMembers() {
        return members;
    }

    public void removeMember(UHCPlayer uhcPlayer) {
        if(members.contains(uhcPlayer)) {
            members.remove(uhcPlayer);
            uhcPlayer.setTeam(null);
            if(members.size() < 2) {
                UHCPlayer removedReminant = null;
                for(UHCPlayer member : members) {
                    removedReminant = member;
                }
                members.remove(removedReminant);
                removedReminant.setTeam(null);
                Bukkit.getPlayer((UUID.fromString(removedReminant.getUuid()))).sendMessage("Your team has been disbanded!");
                Main.currentGame.removeTeam(this);
            }
        }
    }
}
