package org.octave.chestobserver.storage;

import org.bukkit.Material;

public record ActionKey(
        ActionRecord.ActionType type,
        Material material
) {}
