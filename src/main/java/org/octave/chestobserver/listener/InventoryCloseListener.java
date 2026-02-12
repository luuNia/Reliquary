package org.octave.chestobserver.listener;

import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.BlockInventoryHolder;
import org.octave.chestobserver.service.AccessService;

public class InventoryCloseListener implements Listener {

    private final AccessService service;

    public InventoryCloseListener(AccessService service) {
        this.service = service;
    }

    @EventHandler
    public void onClose(InventoryCloseEvent event) {
        if (!(event.getInventory().getHolder() instanceof BlockInventoryHolder bih)) return;
        if (!(event.getPlayer() instanceof org.bukkit.entity.Player player)) return;

        Block block = bih.getBlock();
        service.snapshotClose(player, block, event.getInventory());
    }
}
