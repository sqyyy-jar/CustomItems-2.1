package dev.sqyyy.customitems.ii.persistence;

import dev.sqyyy.customitems.ii.CItemStack;
import org.bukkit.inventory.ItemStack;

public interface AbstractAttributeModifier {
    default void modify(ItemStack itemStack, CItemStack cItemStack) {}
    default void update(ItemStack itemStack, CItemStack cItemStack) {}
}
