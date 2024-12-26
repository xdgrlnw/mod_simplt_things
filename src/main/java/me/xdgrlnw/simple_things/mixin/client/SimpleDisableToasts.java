package me.xdgrlnw.simple_things.mixin.client;

import me.xdgrlnw.simple_things.config.SimpleConfig;
import net.minecraft.client.toast.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ToastManager.class)
public class SimpleDisableToasts {

    @Inject(method = "add(Lnet/minecraft/client/toast/Toast;)V", at = @At("HEAD"), cancellable = true)
    private void onAddToast(Toast toast, CallbackInfo ci) {
        if (disableSystemToasts(toast) || disableRecipeToasts(toast) || disableTutorialToasts(toast)) ci.cancel();
    }

    @Unique
    private boolean disableSystemToasts(Toast toast) {
        return SimpleConfig.client.disableSystemToasts && toast instanceof SystemToast;
    }

    @Unique
    private boolean disableRecipeToasts(Toast toast) {
        return SimpleConfig.client.disableRecipeToasts && toast instanceof RecipeToast;
    }

    @Unique
    private boolean disableTutorialToasts(Toast toast) {
        return SimpleConfig.client.disableTutorialToasts && toast instanceof TutorialToast;
    }
}
