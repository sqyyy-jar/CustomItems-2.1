package dev.sqyyy.customitems.ii;

import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

public class CItemStack {
    public CItemStack(CMaterial m) {

    }

    public static boolean isCustom(ItemStack itemStack) {
        if (!itemStack.hasItemMeta()) return false;
        PersistentDataContainer container = itemStack.getItemMeta().getPersistentDataContainer();
        return container.has(Config.INSTANCE.getCustomItemsKey(), PersistentDataType.TAG_CONTAINER);
    }
}
