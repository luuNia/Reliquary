package org.octave.chestobserver.storage;

import org.bukkit.Material;

import java.util.UUID;

public record ActionRecord(
        UUID playerUuid,
        String playerName,
        long timestamp,
        ActionType type,
        Material material,
        int amount
) {
    public enum ActionType {
        TAKE, PUT
    }
}
