package me.xdgrlnw.simple_things.mixin.common.exp;

import me.xdgrlnw.simple_things.config.SimpleConfig;
import net.minecraft.entity.passive.AnimalEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AnimalEntity.class)
public abstract class DisableXpBreeding {
    @Inject(method =
            "breed(Lnet/minecraft/server/world/ServerWorld;Lnet/minecraft/entity/passive/AnimalEntity;Lnet/minecraft/entity/passive/PassiveEntity;)V",
            at = @At(value = "INVOKE_ASSIGN",
                    target = "Lnet/minecraft/util/math/random/Random;nextInt(I)I"),
            cancellable = true)
    private void breeding(CallbackInfo ci) {
        if (SimpleConfig.common.disableBreedingXp) ci.cancel();
    }
}