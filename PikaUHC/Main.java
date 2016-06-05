package PikaUHC;

import PikaUHC.Commands.UHCCommand;
import PikaUHC.Config.ConfigCommand;
import PikaUHC.Game.Game;
import PikaUHC.Listeners.*;
import PikaUHC.Teams.TeamCommand;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.MaterialData;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin implements Listener {
    public static Game currentGame;

    @Override
    public void onEnable() {
        saveDefaultConfig();
        ItemStack apple = new ItemStack(Material.GOLDEN_APPLE);
        ItemMeta appleMeta = apple.getItemMeta();
        appleMeta.setDisplayName(ChatColor.AQUA + "Golden Skull");
        apple.setItemMeta(appleMeta);

        ShapedRecipe skullRecipe = new ShapedRecipe(apple);
        skullRecipe.shape("GGG", "GAG", "GGG");
        skullRecipe.setIngredient('G', Material.GOLD_INGOT);
        skullRecipe.setIngredient('A', new MaterialData(397,(byte) 3));

        if(Bukkit.addRecipe(skullRecipe)) {
            getLogger().info("Recipe added!");
        } else {
            this.getServer().broadcastMessage("ERROR ADDING RECIPE");
        }
        new MoveListener(this);
        new DeathListener(this);
        new HealthRegenListener(this);
        new LoginListener(this);
        new ChatListener(this);
        new CommandPreprocessListener(this);
        new DamageListener(this);
        new EatListener(this);
        new PortalListener(this);
        new RespawnListener(this);
        new QuitListener(this);
        new InteractListener(this);


        this.getCommand("uhc").setExecutor(new UHCCommand(this));
        this.getCommand("team").setExecutor(new TeamCommand(this));
        this.getCommand("config").setExecutor(new ConfigCommand(this));
    }

    public static Plugin getInstance() {
        return Bukkit.getPluginManager().getPlugin("UHC");
    }
}