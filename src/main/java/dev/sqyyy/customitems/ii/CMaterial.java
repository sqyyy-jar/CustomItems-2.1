package dev.sqyyy.customitems.ii;

import dev.sqyyy.customitems.ii.exceptions.AlreadyRegisteredError;
import dev.sqyyy.customitems.ii.exceptions.KeyNotFoundError;
import dev.sqyyy.customitems.ii.exceptions.NoCustomItemError;
import dev.sqyyy.customitems.ii.persistence.AbstractAttributeModifier;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class CMaterial {
    private static final Map<String, CMaterial> MATERIALS = new HashMap<>();

    private final String key;
    private final Material material;
    private final String displayname;
    private int customModelData = -1;
    private AbstractAttributeModifier modifier;

    public CMaterial(@NotNull String key, @NotNull Material material, @Nullable String displayname) {
        this.key = key.toUpperCase();
        this.material = material;
        this.displayname = displayname == null ? "§rnull" : ChatColor.translateAlternateColorCodes('&', "&r" + displayname);
        this.modifier = new AbstractAttributeModifier() {};
    }

    public static CMaterial getMaterial(String key) throws KeyNotFoundError {
        if (MATERIALS.containsKey(key.toUpperCase())) {
            return MATERIALS.get(key.toUpperCase());
        } else {
            throw new KeyNotFoundError(key.toUpperCase());
        }
    }

    public static CMaterial getMaterial(ItemStack itemStack) throws NoCustomItemError, KeyNotFoundError {
        if (CItemStack.isCustom(itemStack, true)) {
            try {
                return getMaterial(itemStack.getItemMeta().getPersistentDataContainer()
                        .get(Config.INSTANCE.getKeyStorage(), PersistentDataType.STRING));
            } catch (KeyNotFoundError keyNotFoundError) {
                throw new KeyNotFoundError(itemStack.getType().name());
            }

        } else {
            throw new NoCustomItemError(itemStack.getType().name());
        }
    }

    public static boolean isRegistered(CMaterial material) {
        return MATERIALS.containsKey(material.name()) || MATERIALS.containsValue(material);
    }

    public static boolean isRegistered(String key) {
        return MATERIALS.containsKey(key);
    }

    public static void register(CMaterial material) throws AlreadyRegisteredError {
        if (!MATERIALS.containsKey(material.name().toUpperCase()) && !MATERIALS.containsValue(material)) {
            MATERIALS.put(material.name().toUpperCase(), material);
        } else {
            throw new AlreadyRegisteredError(material.name());
        }
    }

    public static CMaterial valueOf(String key) throws KeyNotFoundError {
        return getMaterial(key);
    }

    public static Collection<CMaterial> values() {
        return MATERIALS.values();
    }

    public final void setCustomModelData(int customModelData) {
        this.customModelData = customModelData;
    }

    public final int getCustomModelData() {
        return customModelData;
    }

    public final void setModifier(AbstractAttributeModifier modifier) {
        this.modifier = modifier;
    }

    public AbstractAttributeModifier getModifier() {
        return modifier;
    }

    public final String getDisplayname() {
        return displayname;
    }

    public final String name() {
        return this.key;
    }

    public final Material toBukkit() {
        return this.material;
    }
}
