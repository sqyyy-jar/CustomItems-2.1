package dev.sqyyy.customitems.ii;

import dev.sqyyy.customitems.ii.exceptions.NoCustomItemError;
import dev.sqyyy.customitems.ii.gui.GuiManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin {
    private static Main pl;
    private GuiManager guiManager;

    @Override
    public void onEnable() {
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
