package me.xdgrlnw.simple_things.mixin.common.exp;

import me.xdgrlnw.simple_things.config.SimpleConfig;
import net.minecraft.block.entity.AbstractFurnaceBlockEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AbstractFurnaceBlockEntity.class)
public abstract class DisableXpSmelting {
    @Inject(method = "dropExperience", at = @At("HEAD"), cancellable = true)
    private static void smelting(CallbackInfo ci) {
        if (SimpleConfig.common.disableSmeltingXp) ci.cancel();
    }
}