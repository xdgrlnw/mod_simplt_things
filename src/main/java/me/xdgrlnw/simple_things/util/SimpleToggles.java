package me.xdgrlnw.simple_things.util;

import me.xdgrlnw.simple_things.config.SimpleConfig;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.text.Text;
import org.lwjgl.glfw.GLFW;

public class SimpleToggles {

    private static final KeyBinding TOGGLE_HUD_KEY = KeyBindingHelper.registerKeyBinding(
            new KeyBinding("key.toggle.hud",
                    InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_V, "key.category.simple_things"));

    public static void init() {
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (TOGGLE_HUD_KEY.wasPressed()) {
                toggleHudVisibility(client);
            }
        });
    }

    private static void toggleHudVisibility(net.minecraft.client.MinecraftClient client) {
        SimpleConfig.client.enableHudToolsOverlay = !SimpleConfig.client.enableHudToolsOverlay;
        SimpleConfig.save();
        SimpleConfig.init();
        if (client.player != null) {
            client.player.sendMessage(Text.translatable(
                    SimpleConfig.client.enableHudToolsOverlay ? "key.toggle.hud.on" : "key.toggle.hud.off"), true);
        }
    }
}
