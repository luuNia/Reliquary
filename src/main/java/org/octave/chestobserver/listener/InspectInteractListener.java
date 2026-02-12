package org.octave.chestobserver.listener;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.octave.chestobserver.inspect.InspectSessionManager;
import org.octave.chestobserver.service.AccessService;
import org.octave.chestobserver.storage.ActionKey;
import org.octave.chestobserver.storage.ActionRecord;
import org.octave.chestobserver.util.GradientText;
import org.octave.chestobserver.util.TimeFormatter;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class InspectInteractListener implements Listener {

    private final AccessService service;
    private final InspectSessionManager inspectManager;

    public InspectInteractListener(AccessService service, InspectSessionManager inspectManager) {
        this.service = service;
        this.inspectManager = inspectManager;
    }

    @EventHandler
    public void onInspect(PlayerInteractEvent event) {

        if (event.getAction() != Action.RIGHT_CLICK_BLOCK) return;

        Player player = event.getPlayer();

        if (!inspectManager.isInspecting(player)) return;

        Block block = event.getClickedBlock();
        if (block == null) return;

        event.setCancelled(true);

        player.sendMessage("");
        player.sendMessage(GradientText.apply("✦ Reliquary ✦"));
        player.sendMessage(GradientText.apply("━━━━━━━━━━━━━━━━━━━━━━━━"));
        player.sendMessage("§7📦 Container at §f"
                + block.getX() + " "
                + block.getY() + " "
                + block.getZ());
        player.sendMessage("");

        Map<ActionKey, Integer> grouped = new LinkedHashMap<>();
        Map<ActionKey, Long> timeMap = new HashMap<>();
        Map<ActionKey, String> playerMap = new HashMap<>();

        for (ActionRecord record : service.getActions(block)) {
            ActionKey key = new ActionKey(record.type(), record.material());

            grouped.merge(key, record.amount(), Integer::sum);
            timeMap.putIfAbsent(key, record.timestamp());
            playerMap.putIfAbsent(key, record.playerName());
        }

        if (grouped.isEmpty()) {
            player.sendMessage("§7No records found.");
            player.sendMessage(GradientText.apply("━━━━━━━━━━━━━━━━━━━━━━━━"));
            return;
        }

        for (var entry : grouped.entrySet()) {
            ActionKey key = entry.getKey();
            int total = entry.getValue();

            String timeAgo = TimeFormatter.format(timeMap.get(key));
            String actor = playerMap.get(key);

            String actionText = key.type() == ActionRecord.ActionType.TAKE
                    ? "§c➖ Took "
                    : "§a➕ Put ";

            player.sendMessage(
                    "§e" + actor + " §7• " +
                            actionText +
                            "§f" + total + "x §b" + key.material().name() +
                            " §7(" + timeAgo + ")"
            );
        }

        player.sendMessage(GradientText.apply("━━━━━━━━━━━━━━━━━━━━━━━━"));
    }
}
