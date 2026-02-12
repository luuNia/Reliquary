package org.octave.chestobserver.storage;

import java.util.UUID;

public record AccessRecord(UUID player, long timestamp) {}
