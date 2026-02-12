package org.octave.chestobserver;

import org.bukkit.plugin.java.JavaPlugin;
import org.octave.chestobserver.command.InspectCommand;
import org.octave.chestobserver.inspect.InspectSessionManager;
import org.octave.chestobserver.listener.ContainerAccessListener;
import org.octave.chestobserver.listener.InventoryCloseListener;
import org.octave.chestobserver.listener.InspectInteractListener;
import org.octave.chestobserver.service.AccessService;
import org.octave.chestobserver.storage.MemoryStorage;

public class ChestObserverPlugin extends JavaPlugin {

    @Override
    public void onEnable() {

        saveDefaultConfig();

        InspectSessionManager inspectManager = new InspectSessionManager();
        MemoryStorage storage = new MemoryStorage();
        AccessService accessService = new AccessService(storage, inspectManager);

        getServer().getPluginManager().registerEvents(
                new ContainerAccessListener(accessService), this
        );

        getServer().getPluginManager().registerEvents(
                new InventoryCloseListener(accessService), this
        );

        getServer().getPluginManager().registerEvents(
                new InspectInteractListener(accessService, inspectManager), this
        );

        getCommand("co").setExecutor(new InspectCommand(inspectManager));
    }
}
