package me.xdgrlnw.simple_things.mixin.client;

import me.xdgrlnw.simple_things.config.SimpleConfig;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.BlockState;
import net.minecraft.block.DaylightDetectorBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.util.ActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Environment(value = EnvType.CLIENT)
@Mixin(DaylightDetectorBlock.class)
public class SimpleSoundDayLightDetector {

    @Shadow
    @Final
    public static BooleanProperty INVERTED;

    @Inject(method = "onUse", at = @At("HEAD"))
    public void sound(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit, CallbackInfoReturnable<ActionResult> cir) {
        if (world.isClient && SimpleConfig.client.enableMiscRedstoneSounds) {
            float basePitch = 0.4f;
            float volume = 0.5f;
            float pitch = state.cycle(INVERTED).get(INVERTED) ? basePitch + 0.05f : basePitch;
            world.playSound(player, pos, SoundEvents.BLOCK_COMPARATOR_CLICK, SoundCategory.BLOCKS, volume, pitch);
        }
    }
}
