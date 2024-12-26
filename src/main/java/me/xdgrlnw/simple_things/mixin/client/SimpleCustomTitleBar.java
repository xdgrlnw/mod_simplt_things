package me.xdgrlnw.simple_things.mixin.client;

import me.xdgrlnw.simple_things.config.SimpleConfig;
import net.minecraft.MinecraftVersion;
import net.minecraft.client.MinecraftClient;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(MinecraftClient.class)
public abstract class SimpleCustomTitleBar {

    @Inject(method = "getWindowTitle", at = @At("TAIL"), cancellable = true)
    private void setCustomWindowTitle(CallbackInfoReturnable<String> cir) {
        if (!SimpleConfig.client.enableCustomWindowTitle) return;

        cir.setReturnValue(formatWindowTitle(getMinecraftVersion(), getNickName()));
    }

    @Unique
    private String getMinecraftVersion() {
        return MinecraftVersion.CURRENT.getName() == null ? "" : MinecraftVersion.CURRENT.getName();
    }

    @Unique
    private String getNickName() {
        return MinecraftClient.getInstance().getSession().getUsername() == null ? "" : MinecraftClient.getInstance().getSession().getUsername();
    }

    @Unique
    private String formatWindowTitle(String mcVersion, String nickName) {
        return SimpleConfig.client.customWindowTitle
                .replace("%mc_version%", mcVersion)
                .replace("%nick_name%", nickName);
    }
}
