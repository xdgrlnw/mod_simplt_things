package me.xdgrlnw.simple_things.mixin.common;

import me.xdgrlnw.simple_things.config.SimpleConfig;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(ServerPlayNetworkHandler.class)
public class DisableMovementCheck {

    @Redirect(method = {"onPlayerMove", "onVehicleMove"},
            at = @At(value = "INVOKE", target = "Lnet/minecraft/util/math/Vec3d;lengthSquared()D"))
    public double redirectLengthSquared(Vec3d instance) {
        return SimpleConfig.common.disableMovementCheck ? Float.MAX_VALUE : instance.lengthSquared();
    }

    @ModifyConstant(method = {"onPlayerMove", "onVehicleMove"},
            constant = @Constant(doubleValue = 0.0625))
    private double modifyMoveDoubleConst(double original) {
        return SimpleConfig.common.disableMovementCheck ? Float.MAX_VALUE : original;
    }
}
