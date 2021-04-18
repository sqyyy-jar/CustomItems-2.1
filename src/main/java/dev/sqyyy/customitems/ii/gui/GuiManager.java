package dev.sqyyy.customitems.ii.gui;

import dev.sqyyy.customitems.ii.Main;
import dev.sqyyy.customitems.ii.gui.listeners.InventoryClickListener;
import dev.sqyyy.customitems.ii.gui.listeners.InventoryCloseListener;
import dev.sqyyy.customitems.ii.gui.listeners.PlayerJoinQuitListener;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class GuiManager {
    private final Map<UUID, String> keys;
    private final JavaPlugin pl;
    private final Server se;

    private final ItemStack[] home = new ItemStack[27];

    public GuiManager() {
        this.pl = Main.getPlugin();
        this.se = pl.getServer();
        this.keys = new HashMap<>();
        loadContents();
        for (Player p : Bukkit.getOnlinePlayers()) {
            join(p);
        }
        se.getPluginManager().registerEvents(new InventoryClickListener(), pl);
        se.getPluginManager().registerEvents(new InventoryCloseListener(), pl);
        se.getPluginManager().registerEvents(new PlayerJoinQuitListener(), pl);
    }

    private void loadContents() {
        home[11] = new ItemStack(Material.BARREL);
        home[13] = new ItemStack(Material.BARRIER);
        home[15] = new ItemStack(Material.COMPARATOR);
    }

    public void join(Player p) {
        this.keys.put(p.getUniqueId(), "NONE");
    }

    public void quit(Player p) {
        if (!this.keys.get(p.getUniqueId()).equalsIgnoreCase("NONE")) {
            p.closeInventory();
        }
        this.keys.remove(p);
    }

    public String getGui(UUID uuid) {
        return keys.get(uuid);
    }

    public void open(Player p) {
        keys.put(p.getUniqueId(), "HOME");
        p.openInventory(Bukkit.createInventory(null, 27, "§6CustomItems"));
        p.getOpenInventory().getTopInventory().setContents(home);
    }

    public void close(Player p) {
        keys.put(p.getUniqueId(), "NONE");
    }
}
