package org.octave.chestobserver.util;

import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.inventory.InventoryHolder;

public record BlockKey(String world, int x, int y, int z) {

    public static BlockKey from(Block block) {

        if (block.getState() instanceof Chest chest) {
            InventoryHolder holder = chest.getBlockInventory().getHolder();

            if (holder instanceof Chest mainChest) {
                block = mainChest.getBlock();
            }
        }

        return new BlockKey(
                block.getWorld().getName(),
                block.getX(),
                block.getY(),
                block.getZ()
        );
    }
}
