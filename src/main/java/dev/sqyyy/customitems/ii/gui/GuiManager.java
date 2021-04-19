package dev.sqyyy.customitems.ii.gui;

import dev.sqyyy.customitems.ii.CItemStack;
import dev.sqyyy.customitems.ii.CMaterial;
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
    private final Map<UUID, Integer> pages;
    private final JavaPlugin pl;
    private final Server se;

    private final Map<Integer, ItemStack[]> materialMap = new HashMap<>();
    private final ItemStack[] home = new ItemStack[27];
    private final ItemStack[] settings = new ItemStack[27];
    private final ItemStack[] itemList = new ItemStack[27];

    public GuiManager() {
        this.pl = Main.getPlugin();
        this.se = pl.getServer();
        this.keys = new HashMap<>();
        this.pages = new HashMap<>();
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
        itemList[25] = new ItemBuilder(new ItemStack(Material.ARROW)).setDisplayName("&cBack").build();
        itemList[26] = new ItemBuilder(new ItemStack(Material.ARROW)).setDisplayName("&cFor").build();

        this.materialMap.put(1, itemList.clone());

        int page = 0;
        int i = 0;
        for (int i1 = 0; i1 < CMaterial.values().size(); i1++) {
            if (((double) i) % 18 == 0) {
                page++;
                i = 0;
            }
            if (!this.materialMap.containsKey(page)) this.materialMap.put(page, this.itemList.clone());
            this.materialMap.get(page)[i] = new CItemStack(CMaterial.values().toArray(new CMaterial[CMaterial.values().size()])[i1]).toBukkit();
            i++;
        }
    }

    public void join(HumanEntity p) {
        this.keys.put(p.getUniqueId(), "NONE");
        this.pages.put(p.getUniqueId(), 1);
    }

    public void quit(HumanEntity p) {
        if (!this.keys.get(p.getUniqueId()).equalsIgnoreCase("NONE")) {
            p.closeInventory();
        }
        this.keys.remove(p);
    }

    public void itemListClick(HumanEntity p, int slot, boolean rightclick) {
        if (rightclick) {
            p.sendMessage("§cRightclick not working yet.");
        } else {
            if (p.getInventory().firstEmpty() == -1) {
                p.sendMessage("§cYou have no space in your inventory!");
            } else {
                p.getInventory().addItem(this.materialMap.get(pages.get(p.getUniqueId()))[slot]);
            }
        }
    }

    public int getPage(HumanEntity p) {
        return pages.get(p.getUniqueId());
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

    public void openItemList(HumanEntity p, int page) {
        if (this.materialMap.containsKey(page)) {
            keys.put(p.getUniqueId(), "ITEM_LIST");
            pages.put(p.getUniqueId(), page);
            p.getOpenInventory().getTopInventory().setContents(getItemList(page));
        }
    }

    private ItemStack[] getItemList(int page) {
        return this.materialMap.get(page);
    }

    public void openHome(HumanEntity p) {
        keys.put(p.getUniqueId(), "HOME");
        p.getOpenInventory().getTopInventory().setContents(home);
    }

    public void close(HumanEntity p) {
        keys.put(p.getUniqueId(), "NONE");
    }
}
