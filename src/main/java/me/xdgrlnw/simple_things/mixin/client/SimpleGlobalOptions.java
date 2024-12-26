package me.xdgrlnw.simple_things.mixin.client;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.GameOptions;
import net.minecraft.client.option.KeyBinding;
import org.lwjgl.glfw.GLFW;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GameOptions.class)
abstract class SimpleGlobalOptions {

    @Shadow
    public KeyBinding advancementsKey = new KeyBinding("key.advancements", GLFW.GLFW_KEY_R, KeyBinding.MISC_CATEGORY);

    @Shadow
    protected MinecraftClient client;

    @Inject(method = "load", at = @At("HEAD"))
    private void load(CallbackInfo ci) {
        GameOptions gameOptions = (GameOptions) (Object) this;

        gameOptions.getRealmsNotifications().setValue(false);
        gameOptions.getRealmsNotifications().setValue(false);
        gameOptions.onboardAccessibility = false;
        gameOptions.skipMultiplayerWarning = true;
        gameOptions.joinedFirstServer = true;
        gameOptions.getTelemetryOptInExtra().setValue(false);
        gameOptions.getChatOpacity().setValue(0.85);
        gameOptions.getChatScale().setValue(0.85);
        gameOptions.getChatWidth().setValue(0.60);
        gameOptions.getTextBackgroundOpacity().setValue(0.0);
        gameOptions.getNotificationDisplayTime().setValue(0.5);
        gameOptions.getOperatorItemsTab().setValue(true);
        gameOptions.advancementsKey.setBoundKey(advancementsKey.getDefaultKey());
        gameOptions.language = "ru_ru";
    }

    @Inject(method = "sendClientSettings", at = @At("HEAD"))
    private void sendClientSettings(CallbackInfo ci) {
        GameOptions gameOptions = (GameOptions) (Object) this;
        gameOptions.getRealmsNotifications().setValue(false);
        gameOptions.onboardAccessibility = false;
        gameOptions.skipMultiplayerWarning = true;
        gameOptions.joinedFirstServer = true;
        gameOptions.getTelemetryOptInExtra().setValue(false);
        gameOptions.getChatOpacity().setValue(0.85);
        gameOptions.getChatScale().setValue(0.85);
        gameOptions.getChatWidth().setValue(0.60);
        gameOptions.getTextBackgroundOpacity().setValue(0.0);
        gameOptions.getNotificationDisplayTime().setValue(0.5);
        gameOptions.getOperatorItemsTab().setValue(true);
        gameOptions.advancementsKey.setBoundKey(advancementsKey.getDefaultKey());
        gameOptions.language = "ru_ru";
    }
}