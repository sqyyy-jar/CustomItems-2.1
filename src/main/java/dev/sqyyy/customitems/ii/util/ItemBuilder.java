package dev.sqyyy.customitems.ii.util;

import org.bukkit.ChatColor;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.List;

public class ItemBuilder {
    private final ItemStack item;
    private final ItemMeta meta;

    public ItemBuilder(ItemStack itemStack) {
        this.item = itemStack.clone();
        this.meta = this.item.getItemMeta();
    }

    public ItemBuilder setDisplayName(String var) {
        this.meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', var));
        return this;
    }
    
    public ItemBuilder setLocalizedName(String var) {
        this.meta.setLocalizedName(ChatColor.translateAlternateColorCodes('&', var));
        return this;
    }

    public ItemBuilder setLore(String... var) {
        List<String> var1 = Arrays.asList(var);
        for (int i = 0; i < var1.size(); i++) {
            var1.set(i, ChatColor.translateAlternateColorCodes('&', var1.get(i)));
        }
        this.meta.setLore(var1);
        return this;
    }

    public ItemStack build() {
        this.item.setItemMeta(meta);
        return this.item;
    }
}
