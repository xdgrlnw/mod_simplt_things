package me.xdgrlnw.simple_things;

import me.xdgrlnw.simple_things.config.SimpleConfig;
import me.xdgrlnw.simple_things.util.SimpleToggles;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.ModInitializer;

public class initMain implements ModInitializer, ClientModInitializer {

    @Override
    public void onInitialize() {
        SimpleConfig.init();
    }

    @Override
    public void onInitializeClient() {
        SimpleToggles.init();
    }
}
