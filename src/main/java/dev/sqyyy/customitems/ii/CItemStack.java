package dev.sqyyy.customitems.ii;

import dev.sqyyy.customitems.ii.exceptions.KeyNotFoundError;
import dev.sqyyy.customitems.ii.exceptions.NoCustomItemError;
import dev.sqyyy.customitems.ii.exceptions.NotStackableError;
import org.bukkit.attribute.Attribute;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.UUID;

public class CItemStack {
    private final ItemStack bukkit;
    private final CMaterial material;

    public CItemStack(CMaterial m) throws NoCustomItemError {
        if (!CMaterial.isRegistered(m)) throw new NoCustomItemError(m.name());
        this.bukkit = new ItemStack(m.toBukkit());
        this.material = m;
        init();
    }

    public CItemStack(ItemStack itemStack) throws NoCustomItemError, KeyNotFoundError, NotStackableError {
        this(itemStack, false);
    }

    public CItemStack(ItemStack itemStack, boolean update) throws NoCustomItemError, KeyNotFoundError, NotStackableError {
        if (isCustom(itemStack, true)) {
            this.bukkit = itemStack;
            this.material = CMaterial.getMaterial(itemStack);
            if (!this.material.isStackable() && bukkit.getAmount() > 1) throw new NotStackableError(this.material.name());
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

    public final ItemStack toBukkit() {
        return bukkit;
    }

    private final void init() {
        ItemMeta meta = this.bukkit.getItemMeta();
        meta.setDisplayName(this.material.getDisplayname());
        if (!this.material.isStackable()) {
            meta.getPersistentDataContainer().set(Config.INSTANCE.getUniqueStorage(), PersistentDataType.STRING, UUID.randomUUID().toString());
        }
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
