package PikaUHC.Listeners;

import PikaUHC.Game.GameState;
import PikaUHC.Main;
import org.bukkit.configuration.Configuration;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

/**
 * Created by Jake
 */
public class DamageListener implements Listener {
    public DamageListener(Main instance) {
        instance.getServer().getPluginManager().registerEvents(this, instance);
    }

    @EventHandler
    public void onDamage(EntityDamageByEntityEvent e) {
        if(e.getDamager() instanceof Player && e.getEntity() instanceof Player) {
            if(Main.currentGame.getState() != GameState.PVP) {
                e.setCancelled(true);
                return;
            }
            Configuration config = Main.getInstance().getConfig();
            if(!config.getBoolean("friendly-fire")) {
                if(Main.currentGame.getPlayer((Player) e.getDamager()).getTeam() == Main.currentGame.getPlayer((Player) e.getEntity()).getTeam()) {
                    e.setCancelled(true);
                    return;
                }
            }
        }
    }

    @EventHandler
    void onEntityDamage(EntityDamageByEntityEvent event)
    {
        if(event.getEntity() instanceof Player) {
            Player player = (Player)event.getEntity();
            if (event.getDamager().getType() == EntityType.ENDER_PEARL) {
                if(!Main.getInstance().getConfig().getBoolean("pearl-damage")) {
                    event.setDamage(0);
                    event.setCancelled(true);
                }
            }
        }
    }
}
