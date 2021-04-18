package dev.sqyyy.customitems.ii;

import org.bukkit.NamespacedKey;

public enum Config {
    INSTANCE;

    private final NamespacedKey keyStorage = new NamespacedKey(Main.getPlugin(Main.class), "key");
    public NamespacedKey getKeyStorage() {
        return keyStorage;
    }
}
