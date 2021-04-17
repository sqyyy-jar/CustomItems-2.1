package dev.sqyyy.customitems.ii;

import dev.sqyyy.customitems.ii.exceptions.AlreadyRegisteredError;
import dev.sqyyy.customitems.ii.exceptions.KeyNotFoundError;
import org.bukkit.Material;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class CMaterial {
    private static final Map<String, CMaterial> MATERIALS = new HashMap<>();

    private final String key;
    private final Material m;

    public CMaterial(@NotNull String key, @NotNull Material material) {
        this.key = key.toUpperCase();
        this.m = material;
    }

    public static CMaterial getMaterial(String key) throws KeyNotFoundError {
        if (MATERIALS.containsKey(key.toUpperCase())) {
            return MATERIALS.get(key.toUpperCase());
        } else {
            throw new KeyNotFoundError(key.toUpperCase());
        }
    }

    public static boolean isRegistered(CMaterial material) {
        return MATERIALS.containsKey(material.name()) || MATERIALS.containsValue(material);
    }

    public static void register(CMaterial material) throws AlreadyRegisteredError {
        if (!MATERIALS.containsKey(material.name().toUpperCase()) && !MATERIALS.containsValue(material)) {
            MATERIALS.put(material.name().toUpperCase(), material);
        } else {
            throw new AlreadyRegisteredError(material.name());
        }
    }

    public final String name() {
        return this.key;
    }

    public final Material toBukkit() {
        return this.m;
    }

    public static Collection<CMaterial> values() {
        return MATERIALS.values();
    }

    public static CMaterial valueOf(String key) throws KeyNotFoundError {
        return getMaterial(key);
    }
}
