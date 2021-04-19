package dev.sqyyy.customitems.ii.gui.listeners;

import dev.sqyyy.customitems.ii.Main;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;

public class InventoryCloseListener implements Listener {
    @EventHandler
    public void onClose(InventoryCloseEvent e) {
        Main.getPlugin().getGuiManager().close(e.getPlayer());
    }
}
