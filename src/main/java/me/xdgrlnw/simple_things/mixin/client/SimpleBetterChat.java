package me.xdgrlnw.simple_things.mixin.client;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import me.xdgrlnw.simple_things.config.SimpleConfig;
import net.minecraft.client.gui.hud.ChatHud;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ChatHud.class)
public class SimpleBetterChat {
    @ModifyExpressionValue(method = {"addMessage(Lnet/minecraft/text/Text;)V", "addToMessageHistory", "addVisibleMessage"},
            at = @At(value = "CONSTANT", args = "intValue=100"))
    public int maxChatLines(int original) {
        return SimpleConfig.client.maxChatLines;
    }

    @Inject(method = "clear", at = @At("HEAD"), cancellable = true)
    public void saveChatOnRelog(boolean clearHistory, CallbackInfo ci) {
        if (clearHistory && SimpleConfig.client.saveChatOnRelog) {
            ci.cancel();
        }
    }
}


