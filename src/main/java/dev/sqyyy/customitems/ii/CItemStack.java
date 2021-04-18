package dev.sqyyy.customitems.ii;

import dev.sqyyy.customitems.ii.exceptions.KeyNotFoundError;
import dev.sqyyy.customitems.ii.exceptions.NoCustomItemError;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

public class CItemStack {
    private final ItemStack bukkit;
    private final CMaterial material;

    public CItemStack(CMaterial m) {
        this.bukkit = new ItemStack(m.toBukkit());
        this.material = m;
        init();
    }

    public CItemStack(ItemStack itemStack) throws NoCustomItemError, KeyNotFoundError {
        this(itemStack, false);
    }

    public CItemStack(ItemStack itemStack, boolean update) throws NoCustomItemError, KeyNotFoundError {
        if (isCustom(itemStack, true)) {
            this.bukkit = itemStack;
            this.material = CMaterial.getMaterial(itemStack);
            if (update) update();
        } else {
            throw new NoCustomItemError(itemStack.getType().name());
        }
    }

    public static boolean isCustom(ItemStack itemStack) {
        if (!itemStack.hasItemMeta()) return false;
        PersistentDataContainer container = itemStack.getItemMeta().getPersistentDataContainer();
        return container.has(Config.INSTANCE.getKeyStorage(), PersistentDataType.STRING);
    }

    public static boolean isCustom(ItemStack itemStack, boolean checkKey) {
        if (!isCustom(itemStack)) return false;
        if (!checkKey) {
            return isCustom(itemStack);
        } else {
            if (isCustom(itemStack)) {
                return CMaterial.isRegistered(itemStack.getItemMeta().getPersistentDataContainer()
                        .get(Config.INSTANCE.getKeyStorage(), PersistentDataType.STRING));
            } else {
                return false;
            }
        }
    }

    public ItemStack toBukkit() {
        return bukkit;
    }

    private final void init() {
        ItemMeta meta = this.bukkit.getItemMeta();
        meta.setDisplayName(this.material.getDisplayname());
        PersistentDataContainer container = meta.getPersistentDataContainer();
        container.set(Config.INSTANCE.getKeyStorage(), PersistentDataType.STRING, this.material.name());
        if (this.material.getCustomModelData() != -1) meta.setCustomModelData(this.material.getCustomModelData());
        this.bukkit.setItemMeta(meta);
        this.material.getModifier().modify(this.bukkit, this);
    }

    private final void update() {
        ItemMeta meta = this.bukkit.getItemMeta();
        meta.setDisplayName(this.material.getDisplayname());
        if (this.material.getCustomModelData() != -1 && meta.getCustomModelData() != this.material.getCustomModelData()) meta.setCustomModelData(this.material.getCustomModelData());
        this.bukkit.setItemMeta(meta);
        this.material.getModifier().update(this.bukkit, this);
    }

}
