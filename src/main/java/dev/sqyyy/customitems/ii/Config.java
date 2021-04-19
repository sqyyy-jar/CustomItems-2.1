package dev.sqyyy.customitems.ii;

import org.bukkit.NamespacedKey;

public enum Config {
    INSTANCE;

    private final NamespacedKey keyStorage = new NamespacedKey(Main.getPlugin(Main.class), "ci-key");
    private final NamespacedKey uniqueStorage = new NamespacedKey(Main.getPlugin(Main.class), "ci-unique");
    public NamespacedKey getKeyStorage() {
        return keyStorage;
    }
    public NamespacedKey getUniqueStorage() {
        return uniqueStorage;
    }
}
