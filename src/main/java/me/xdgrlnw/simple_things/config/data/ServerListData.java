package me.xdgrlnw.simple_things.config.data;

import java.util.HashMap;
import java.util.Map;

public class ServerListData {

    private final Map<String, String> defaultServers;

    public ServerListData() {
        defaultServers = new HashMap<>();
        defaultServers.put("Survival | Simple Vanilla", "mc.xdgrlnw.ru:25565");
        defaultServers.put("Creative | Simple Vanilla", "mc.xdgrlnw.ru:25566");
    }

    public ServerListData(Map<String, String> defaultServers) {
        this.defaultServers = defaultServers;
    }

    public Map<String, String> getDefaultServers() {
        return new HashMap<>(defaultServers);
    }
}