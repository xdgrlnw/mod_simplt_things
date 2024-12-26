package me.xdgrlnw.simple_things.mixin.client;

import com.mojang.serialization.Lifecycle;
import me.xdgrlnw.simple_things.config.SimpleConfig;
import net.minecraft.server.integrated.IntegratedServerLoader;
import net.minecraft.world.SaveProperties;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(IntegratedServerLoader.class)
public abstract class SimpleDisableExperimentalWarning {

    @ModifyVariable(method = "tryLoad", at = @At("HEAD"), argsOnly = true, index = 4)
    private static boolean modifyCreateWorldWarning(boolean original) {
        return SimpleConfig.client.disableExperimentalWarning;
    }

    @Redirect(method = "checkBackupAndStart", at = @At(value = "INVOKE",
            target = "Lnet/minecraft/world/SaveProperties;getLifecycle()Lcom/mojang/serialization/Lifecycle;"))
    private Lifecycle redirectGetLifecycle(SaveProperties saveProperties) {
        return SimpleConfig.client.disableExperimentalWarning ? Lifecycle.stable() : saveProperties.getLifecycle();
    }
}
