package org.octave.chestobserver.inspect;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import org.bukkit.entity.Player;

public class InspectSessionManager {

    private final Set<UUID> inspectors = new HashSet<>();

    public boolean isInspecting(Player player) {
        return inspectors.contains(player.getUniqueId());
    }

    public boolean toggle(Player player) {
        UUID id = player.getUniqueId();
        if (inspectors.contains(id)) {
            inspectors.remove(id);
            return false;
        } else {
            inspectors.add(id);
            return true;
        }
    }
}
