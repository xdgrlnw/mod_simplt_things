package me.xdgrlnw.simple_things.mixin.client;

import me.xdgrlnw.simple_things.config.SimpleConfig;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.Window;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(MinecraftClient.class)
public abstract class SimpleDisableAutoFullScreen {
    @Shadow
    @Final
    private Window window;

    @Redirect(method = "<init>(Lnet/minecraft/client/RunArgs;)V", at = @At(value = "INVOKE",
            target = "Lnet/minecraft/client/util/Window;toggleFullscreen()V"))
    private void disableAutoFullScreen(Window window) {
        if (!SimpleConfig.client.disableAutoFullScreen) this.window.toggleFullscreen();
    }
}
