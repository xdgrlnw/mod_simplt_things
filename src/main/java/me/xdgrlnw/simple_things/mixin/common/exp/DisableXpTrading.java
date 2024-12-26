package me.xdgrlnw.simple_things.mixin.common.exp;

import me.xdgrlnw.simple_things.config.SimpleConfig;
import net.minecraft.village.TradeOffer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(TradeOffer.class)
public abstract class DisableXpTrading {
    @Inject(method =
            "shouldRewardPlayerExperience",
            at = @At(value = "HEAD"),
            cancellable = true)
    private void trading(CallbackInfoReturnable<Boolean> cir) {
        if (SimpleConfig.common.disableTradingXp) cir.setReturnValue(false);
    }
}