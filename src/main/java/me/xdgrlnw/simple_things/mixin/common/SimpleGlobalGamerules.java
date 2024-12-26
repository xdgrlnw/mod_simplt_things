package me.xdgrlnw.simple_things.mixin.common;

import me.xdgrlnw.simple_things.config.SimpleConfig;
import me.xdgrlnw.simple_things.config.data.CommonData;
import me.xdgrlnw.simple_things.util.SimpleLogger;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.GameRules;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MinecraftServer.class)
public class SimpleGlobalGamerules {

    @Unique
    private static void applyServerGamerules(GameRules gameRules) {
        CommonData config = SimpleConfig.common;
        if (!config.enableGlobalGameRules) return;

        gameRules.get(GameRules.DISABLE_ELYTRA_MOVEMENT_CHECK).set(config.gameruleDisableElytraMovementCheck, null);
        gameRules.get(GameRules.DISABLE_PLAYER_MOVEMENT_CHECK).set(config.gameruleDisablePlayerMovementCheck, null);
        gameRules.get(GameRules.REDUCED_DEBUG_INFO).set(config.gameruleReducedDebugInfo, null);
        gameRules.get(GameRules.SPAWN_CHUNK_RADIUS).set(config.gameruleSpawnChunkRadius, null);
        gameRules.get(GameRules.PLAYERS_SLEEPING_PERCENTAGE).set(config.gamerulePlayerSleepingPercentage, null);

        SimpleLogger.log("Server GameRules applied from config.");
    }

    @Inject(method = "loadWorld", at = @At(value = "TAIL"))
    private void setServerGamerules(CallbackInfo info) {
        GameRules gameRules = ((MinecraftServer) (Object) this).getGameRules();
        applyServerGamerules(gameRules);
    }
}
