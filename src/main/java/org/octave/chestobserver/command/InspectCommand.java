package org.octave.chestobserver.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.octave.chestobserver.inspect.InspectSessionManager;

public class InspectCommand implements CommandExecutor {

    private final InspectSessionManager manager;

    public InspectCommand(InspectSessionManager manager) {
        this.manager = manager;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!(sender instanceof Player player)) {
            sender.sendMessage("Only players can use this command.");
            return true;
        }

        if (!player.hasPermission("chestobserver.inspect")) {
            player.sendMessage("§cNo permission.");
            return true;
        }

        boolean enabled = manager.toggle(player);

        if (enabled) {
            player.sendMessage("§aInspect mode ENABLED");
            player.sendMessage("§7Right-click a container to view logs.");
        } else {
            player.sendMessage("§cInspect mode DISABLED");
        }

        return true;
    }
}
