package me.xdgrlnw.simple_things.mixin.common.exp;

import me.xdgrlnw.simple_things.config.SimpleConfig;
import net.minecraft.block.Block;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Block.class)
public abstract class DisableXpMining {
    @Inject(method = "dropExperience", at = @At("HEAD"), cancellable = true)
    private void mining(CallbackInfo ci) {
        if (SimpleConfig.common.disableMiningXp) ci.cancel();
    }
}