package dev.sqyyy.customitems.ii;

import dev.sqyyy.customitems.ii.exceptions.AlreadyRegisteredError;
import dev.sqyyy.customitems.ii.exceptions.NoCustomItemError;
import dev.sqyyy.customitems.ii.gui.GuiManager;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.Set;

public final class Main extends JavaPlugin {
    private static Main pl;
    private GuiManager guiManager;

    @Override
    public void onEnable() {
        saveDefaultConfig();
        File f = new File(this.getDataFolder(), "items.yml");
        if (!f.exists()) {
            try {
                f.createNewFile();
                System.out.println("Default items file created.");
            } catch (IOException e) {
                System.out.println("Could not save default items.yml file.");
            }
        } else {
            FileConfiguration items = YamlConfiguration.loadConfiguration(f);
            Set<String> itemSet = items.getKeys(false);
            for (String s : itemSet) {
                if (items.contains(s + ".key") && items.contains(s + ".name") && items.contains(s + ".material")
                        && items.contains(s + ".stackable") && items.getString(s + ".key") != null
                        && items.getString(s + ".name") != null
                        && items.getString(s + ".material") != null) {
                    try {
                        CMaterial m = new CMaterial(items.getString(s + ".key"), Material.valueOf(
                                items.getString(s + ".material").toUpperCase()),
                                items.getString(s + ".name"), items.getBoolean(s + ".stackable"));
                        try {
                            CMaterial.register(m);
                        } catch (AlreadyRegisteredError alreadyRegisteredError) {
                            System.out.println("Could not register " + m.name());
                        }
                    } catch (IllegalArgumentException e) {
                        System.out.println("Invalid material of item '" + items.getString(s + ".key") + "'.");
                    }
                } else {
                    System.out.println("Tried loading invalid item: " + (items.contains(s + ".key") ? items.getString(s + ".key") : ""));
                }
            }
        }
        pl = this;
        try {
            this.guiManager = new GuiManager();
        } catch (NoCustomItemError noCustomItemError) {
            System.out.println("Could not load GuiManager!");
        }
    }

    @Override
    public void onDisable() {
        for (Player p : Bukkit.getOnlinePlayers()) {
            p.closeInventory();
            guiManager.close(p);
        }
    }

    public static Main getPlugin() {
        return pl;
    }

    public GuiManager getGuiManager() {
        return guiManager;
    }
}
