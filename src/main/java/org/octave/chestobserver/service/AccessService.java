package org.octave.chestobserver.service;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.octave.chestobserver.inspect.InspectSessionManager;
import org.octave.chestobserver.storage.*;
import org.octave.chestobserver.util.BlockKey;

import java.util.Map;

public class AccessService {

    private final MemoryStorage storage;
    private final SnapshotStorage snapshotStorage = new SnapshotStorage();
    private final InspectSessionManager inspectManager;

    public AccessService(MemoryStorage storage, InspectSessionManager inspectManager) {
        this.storage = storage;
        this.inspectManager = inspectManager;
    }

    public void snapshotOpen(Player player, Inventory inventory) {
        if (inspectManager.isInspecting(player)) return;
        snapshotStorage.put(player.getUniqueId(), new InventorySnapshot(inventory));
    }

    public void snapshotClose(Player player, Block block, Inventory inventory) {
        if (inspectManager.isInspecting(player)) return;

        InventorySnapshot before = snapshotStorage.remove(player.getUniqueId());
        if (before == null) return;

        InventorySnapshot after = new InventorySnapshot(inventory);

        for (Map.Entry<String, Integer> entry : before.getContents().entrySet()) {
            String materialKey = entry.getKey();
            int beforeAmount = entry.getValue();
            int afterAmount = after.getContents().getOrDefault(materialKey, 0);

            int diff = afterAmount - beforeAmount;

            if (diff < 0) {
                log(block, player, ActionRecord.ActionType.TAKE,
                        Material.valueOf(materialKey), -diff);
            } else if (diff > 0) {
                log(block, player, ActionRecord.ActionType.PUT,
                        Material.valueOf(materialKey), diff);
            }
        }

        for (Map.Entry<String, Integer> entry : after.getContents().entrySet()) {
            if (!before.getContents().containsKey(entry.getKey())) {
                log(block, player, ActionRecord.ActionType.PUT,
                        Material.valueOf(entry.getKey()), entry.getValue());
            }
        }
    }


    public Iterable<ActionRecord> getActions(Block block) {
        return storage.getActions(BlockKey.from(block));
    }

    private void log(Block block, Player player,
                     ActionRecord.ActionType type,
                     Material material, int amount) {

        storage.logAction(
                BlockKey.from(block),
                new ActionRecord(
                        player.getUniqueId(),
                        player.getName(),
                        System.currentTimeMillis(),
                        type,
                        material,
                        amount
                )
        );
    }
}