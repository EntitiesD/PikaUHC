package PikaUHC.Listeners;

import PikaUHC.Main;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.Configuration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.potion.Potion;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.potion.PotionType;

/**
 * Created by Jake
 */
public class EatListener implements Listener {
    public EatListener(Main instance) {
        instance.getServer().getPluginManager().registerEvents(this, instance);
    }

    @EventHandler
    public void onEat(final PlayerItemConsumeEvent e) {
        if(e.getItem().getType() == Material.GOLDEN_APPLE) {
            if(e.getItem().getItemMeta().getDisplayName() != null) {
                if(ChatColor.stripColor(e.getItem().getItemMeta().getDisplayName()) == "Golden Skull") {
                    e.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 160, 0));
                }
            }

        } else if(e.getItem().getType() == Material.POTION) {
            Potion potion = (Potion) e.getItem().getItemMeta();
            if(potion.getType() == PotionType.STRENGTH) {
                Configuration config = Main.getInstance().getConfig();
                if(potion.getLevel() == 1) {
                    if(!config.getBoolean("strength1-enabled")) {
                        e.setCancelled(true);
                    }
                } else if(potion.getLevel() == 2) {
                    if(!config.getBoolean("strength2-enabled")) {
                        e.setCancelled(true);
                    }
                }
            }
        }
    }
}
