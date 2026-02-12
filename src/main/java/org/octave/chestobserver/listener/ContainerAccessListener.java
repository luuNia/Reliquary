package org.octave.chestobserver.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.BlockInventoryHolder;
import org.octave.chestobserver.service.AccessService;

public class ContainerAccessListener implements Listener {

    private final AccessService service;

    public ContainerAccessListener(AccessService service) {
        this.service = service;
    }

    @EventHandler
    public void onInventoryOpen(InventoryOpenEvent event) {

        if (!(event.getPlayer() instanceof Player player)) return;
        if (!(event.getInventory().getHolder() instanceof BlockInventoryHolder)) return;

        service.snapshotOpen(player, event.getInventory());
    }
}
