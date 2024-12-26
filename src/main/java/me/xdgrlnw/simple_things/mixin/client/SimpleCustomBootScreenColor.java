package me.xdgrlnw.simple_things.mixin.client;

import me.xdgrlnw.simple_things.config.SimpleConfig;
import me.xdgrlnw.simple_things.config.SimpleConfigUtil;
import net.minecraft.client.gui.screen.SplashOverlay;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.IntSupplier;

@Mixin(SplashOverlay.class)
public abstract class SimpleCustomBootScreenColor {
    @Mutable
    @Final
    @Shadow
    private static IntSupplier BRAND_ARGB;

    @Inject(method = "<clinit>", at = @At("TAIL"))
    private static void changeColor(CallbackInfo ci) {
        if (SimpleConfig.client.enableBootScreenReColor)
            BRAND_ARGB = () -> SimpleConfigUtil.getColorFromHex(SimpleConfig.client.bootScreenColor);
    }
}
