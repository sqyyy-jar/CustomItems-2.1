package dev.sqyyy.customitems.ii.gui.listeners;

import dev.sqyyy.customitems.ii.Main;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerJoinQuitListener implements Listener {
    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Main.getPlugin().getGuiManager().join(e.getPlayer());
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        Main.getPlugin().getGuiManager().quit(e.getPlayer());
    }
}
