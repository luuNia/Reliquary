package org.octave.chestobserver.storage;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class SnapshotStorage {

    private final Map<UUID, InventorySnapshot> snapshots = new HashMap<>();

    public void put(UUID player, InventorySnapshot snapshot) {
        snapshots.put(player, snapshot);
    }

    public InventorySnapshot remove(UUID player) {
        return snapshots.remove(player);
    }
}
