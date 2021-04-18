package dev.sqyyy.customitems.ii.gui.commands;

import dev.sqyyy.customitems.ii.Main;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class CustomItemsCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("§c§lYou have to be a player to execute this command!");
            return true;
        }
        if (args.length != 0) {
            return false;
        }
        Player p = (Player) sender;
        Main.getPlugin().getGuiManager().open(p);
        return true;
    }
}
