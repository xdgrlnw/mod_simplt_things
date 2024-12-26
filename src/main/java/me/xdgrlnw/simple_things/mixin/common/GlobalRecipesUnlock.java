package me.xdgrlnw.simple_things.mixin.common;

import me.xdgrlnw.simple_things.config.SimpleConfig;
import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.ArrayList;

@Mixin(ServerPlayerEntity.class)
public class GlobalRecipesUnlock {

    @Inject(method = "onSpawn", at = @At("TAIL"))
    private void unlockRecipes(CallbackInfo ci) {
        if (SimpleConfig.common.unlockRecipes) {
            ServerPlayerEntity player = (ServerPlayerEntity) (Object) this;
            player.unlockRecipes(new ArrayList<>(player.server.getRecipeManager().values()));
        }
    }
}

