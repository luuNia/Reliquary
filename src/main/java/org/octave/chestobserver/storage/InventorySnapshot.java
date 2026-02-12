package org.octave.chestobserver.storage;

import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

public class InventorySnapshot {

    private final Map<String, Integer> contents = new HashMap<>();

    public InventorySnapshot(Inventory inventory) {
        for (ItemStack item : inventory.getContents()) {
            if (item == null) continue;
            String key = item.getType().name();
            contents.merge(key, item.getAmount(), Integer::sum);
        }
    }

    public Map<String, Integer> getContents() {
        return contents;
    }
}
