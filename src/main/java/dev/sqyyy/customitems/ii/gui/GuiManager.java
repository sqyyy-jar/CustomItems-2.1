package dev.sqyyy.customitems.ii.gui;

import dev.sqyyy.customitems.ii.Main;
import dev.sqyyy.customitems.ii.gui.listeners.InventoryClickListener;
import dev.sqyyy.customitems.ii.gui.listeners.InventoryCloseListener;
import dev.sqyyy.customitems.ii.gui.listeners.PlayerJoinQuitListener;
import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class GuiManager {
    private final Map<UUID, String> keys;
    private final JavaPlugin pl;
    private final Server se;

    public GuiManager() {
        this.pl = Main.getPlugin();
        this.se = pl.getServer();
        this.keys = new HashMap<>();
        for (Player p : Bukkit.getOnlinePlayers()) {
            join(p);
        }
        se.getPluginManager().registerEvents(new InventoryClickListener(), pl);
        se.getPluginManager().registerEvents(new InventoryCloseListener(), pl);
        se.getPluginManager().registerEvents(new PlayerJoinQuitListener(), pl);
    }

    public void join(Player p) {
        this.keys.put(p.getUniqueId(), "NONE");
    }

    public void quit(Player p) {
        this.keys.remove(p);
    }
}
