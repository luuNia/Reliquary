package org.octave.chestobserver.storage;

import java.util.*;
import org.octave.chestobserver.util.BlockKey;

public class MemoryStorage {

    private static final int MAX_HISTORY = 10;

    private final Map<BlockKey, Deque<ActionRecord>> actions = new HashMap<>();

    public void logAction(BlockKey key, ActionRecord record) {
        actions.computeIfAbsent(key, k -> new ArrayDeque<>()).addFirst(record);
        while (actions.get(key).size() > MAX_HISTORY) {
            actions.get(key).removeLast();
        }
    }

    public Deque<ActionRecord> getActions(BlockKey key) {
        return actions.getOrDefault(key, new ArrayDeque<>());
    }
}
