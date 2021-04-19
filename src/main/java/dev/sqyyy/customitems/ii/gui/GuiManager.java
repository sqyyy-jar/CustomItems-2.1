package dev.sqyyy.customitems.ii.gui;

import dev.sqyyy.customitems.ii.Main;
import dev.sqyyy.customitems.ii.gui.commands.CustomItemsCommand;
import dev.sqyyy.customitems.ii.gui.listeners.*;
import dev.sqyyy.customitems.ii.util.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Server;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.*;

public class GuiManager {
    private final Map<UUID, String> keys;
    private final JavaPlugin pl;
    private final Server se;

    private final ItemStack[] home = new ItemStack[27];
    private final ItemStack[] settings = new ItemStack[27];
    private final ItemStack[] itemList = new ItemStack[27];

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
        pl.getCommand("customitems").setExecutor(new CustomItemsCommand());
    }

    private void loadContents() {
        home[11] = new ItemBuilder(new ItemStack(Material.BARREL)).setDisplayName("&b&lAll items").build();
        home[13] = new ItemBuilder(new ItemStack(Material.BARRIER)).setDisplayName("&c&lClose").build();
        home[15] = new ItemBuilder(new ItemStack(Material.COMPARATOR)).setDisplayName("&6&lSettings").build();

        settings[18] = new ItemBuilder(new ItemStack(Material.BARRIER)).setDisplayName("&cClose").build();
        settings[19] = new ItemBuilder(new ItemStack(Material.RED_STAINED_GLASS_PANE)).setDisplayName("&cBack").build();

        updateList();
    }

    private void updateList() {
        itemList[18] = new ItemBuilder(new ItemStack(Material.BARRIER)).setDisplayName("&cClose").build();
        itemList[19] = new ItemBuilder(new ItemStack(Material.RED_STAINED_GLASS_PANE)).setDisplayName("&cBack").build();
    }

    public void join(HumanEntity p) {
        this.keys.put(p.getUniqueId(), "NONE");
    }

    public void quit(HumanEntity p) {
        if (!this.keys.get(p.getUniqueId()).equalsIgnoreCase("NONE")) {
            p.closeInventory();
        }
        this.keys.remove(p);
    }

    public String getGui(UUID uuid) {
        return keys.get(uuid);
    }

    public void open(HumanEntity p) {
        p.openInventory(Bukkit.createInventory(null, 27, "§6CustomItems"));
        openHome(p);
    }

    public void openSettings(HumanEntity p) {
        keys.put(p.getUniqueId(), "SETTINGS");
        p.getOpenInventory().getTopInventory().setContents(settings);
    }

    public void openItemlist(HumanEntity p) {
        keys.put(p.getUniqueId(), "ITEM_LIST");
        p.getOpenInventory().getTopInventory().setContents(itemList);
    }

    public void openHome(HumanEntity p) {
        keys.put(p.getUniqueId(), "HOME");
        p.getOpenInventory().getTopInventory().setContents(home);
    }

    public void close(HumanEntity p) {
        keys.put(p.getUniqueId(), "NONE");
    }
}
