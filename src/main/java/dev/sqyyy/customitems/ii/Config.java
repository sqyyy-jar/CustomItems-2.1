package dev.sqyyy.customitems.ii;

import org.bukkit.NamespacedKey;

public enum Config {
    INSTANCE;

    private final NamespacedKey key = new NamespacedKey(Main.getPlugin(Main.class), "custom_items");
    public NamespacedKey getCustomItemsKey() {
        return key;
    }
}
