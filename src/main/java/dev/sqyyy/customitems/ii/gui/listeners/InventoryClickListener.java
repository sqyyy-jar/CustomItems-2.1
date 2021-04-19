package dev.sqyyy.customitems.ii.gui.listeners;

import dev.sqyyy.customitems.ii.Main;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;

public class InventoryClickListener implements Listener {
    @EventHandler
    public void onClick(InventoryClickEvent e) {
        if (Main.getPlugin().getGuiManager().getGui(e.getWhoClicked().getUniqueId()).equalsIgnoreCase("NONE")) return;
        e.setCancelled(true);
        if (e.getAction() == InventoryAction.CLONE_STACK || e.getAction() == InventoryAction.COLLECT_TO_CURSOR
        || e.getAction() == InventoryAction.DROP_ALL_CURSOR || e.getAction() == InventoryAction.DROP_ALL_SLOT
        || e.getAction() == InventoryAction.DROP_ONE_CURSOR || e.getAction() == InventoryAction.DROP_ONE_SLOT
        || e.getAction() == InventoryAction.HOTBAR_MOVE_AND_READD || e.getAction() == InventoryAction.HOTBAR_SWAP
        || e.getAction() == InventoryAction.SWAP_WITH_CURSOR) {
            return;
        }
        if (e.getCurrentItem() == null) return;
        if (e.getClickedInventory() != e.getView().getTopInventory()) return;
        switch (Main.getPlugin().getGuiManager().getGui(e.getWhoClicked().getUniqueId()).toUpperCase()) {
            case "HOME":
                switch (e.getCurrentItem().getType()) {
                    case BARRIER:
                        e.getWhoClicked().closeInventory();
                        Main.getPlugin().getGuiManager().close(e.getWhoClicked());
                        break;
                    case BARREL:
                        Main.getPlugin().getGuiManager().openItemList(e.getWhoClicked(), 1);
                        break;
                    case COMPARATOR:
                        Main.getPlugin().getGuiManager().openSettings(e.getWhoClicked());
                        break;
                }
                break;
            case "SETTINGS":
                switch (e.getCurrentItem().getType()) {
                    case BARRIER:
                        e.getWhoClicked().closeInventory();
                        Main.getPlugin().getGuiManager().close(e.getWhoClicked());
                        break;
                    case RED_STAINED_GLASS_PANE:
                        Main.getPlugin().getGuiManager().openHome(e.getWhoClicked());
                        break;
                }
                break;
            case "ITEM_LIST":
                switch (e.getSlot()) {
                    case 18:
                        e.getWhoClicked().closeInventory();
                        Main.getPlugin().getGuiManager().close(e.getWhoClicked());
                        break;
                    case 19:
                        Main.getPlugin().getGuiManager().openHome(e.getWhoClicked());
                        break;
                    default:
                        Main.getPlugin().getGuiManager().itemListClick(e.getWhoClicked(), e.getSlot(), e.isRightClick());
                }
                break;
        }
    }
}
