package me.xdgrlnw.simple_things.mixin.client;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import me.xdgrlnw.simple_things.util.SimpleLogger;
import net.minecraft.client.network.ServerInfo;
import net.minecraft.client.option.ServerList;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;

@Mixin(ServerList.class)
public class SimpleGlobalServers {

    @Inject(method = "loadFile", at = @At(value = "RETURN"))
    private void loadDefaultServers(CallbackInfo ci) {
        ServerList serverList = (ServerList) (Object) this;
        Path path = Path.of("config/simple_things/default_servers.json");

        if (!Files.exists(path)) {
            SimpleLogger.logWarning("Default server list file not found at: " + path);
            return;
        }

        try (Reader reader = Files.newBufferedReader(path)) {
            JsonObject rootObject = JsonParser.parseReader(reader).getAsJsonObject();

            if (!rootObject.has("defaultServers") || !rootObject.get("defaultServers").isJsonObject()) {
                SimpleLogger.logError("Invalid JSON format: Expected 'serverList' object.");
                return;
            }

            JsonObject servers = rootObject.getAsJsonObject("defaultServers");

            for (var entry : servers.entrySet()) {
                String name = entry.getKey();
                String address = entry.getValue().getAsString();

                if (address.isEmpty()) {
                    SimpleLogger.logError("Skipping server \"" + name + "\" due to empty address.");
                    continue;
                }

                if (serverExists(serverList, name, address)) {
                    SimpleLogger.log("Server \"" + name + "\" or address \"" + address + "\" already exists in the list. Skipping...");
                    continue;
                }

                addServerToList(serverList, name, address);
            }
        } catch (IOException e) {
            SimpleLogger.logError("Failed to read the server list: " + e.getMessage());
        }
    }

    @Unique
    private boolean serverExists(ServerList serverList, String name, String address) {
        for (int i = 0; i < serverList.size(); i++) {
            ServerInfo existingServer = serverList.get(i);
            if (existingServer.name.equals(name) && existingServer.address.equals(address)) {
                return true;
            }
        }
        return false;
    }

    @Unique
    private void addServerToList(ServerList serverList, String name, String address) {
        serverList.add(new ServerInfo(name, address, ServerInfo.ServerType.OTHER), false);
        SimpleLogger.log("Added server: " + name + " with address: " + address);
    }
}
