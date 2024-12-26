package me.xdgrlnw.simple_things.mixin.client;

import me.xdgrlnw.simple_things.config.SimpleConfig;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Environment(EnvType.CLIENT)
@Mixin(ItemStack.class)
public abstract class SimpleHudDurabilityTooltip {

    @Inject(method = "getTooltip", at = @At("RETURN"), cancellable = true)
    private void appendDurabilityTooltip(Item.TooltipContext context, PlayerEntity player, TooltipType type, CallbackInfoReturnable<List<Text>> cir) {
        if (!shouldAddDurabilityTooltip(type)) return;

        ItemStack itemStack = (ItemStack) (Object) this;
        int currentDurability = itemStack.getMaxDamage() - itemStack.getDamage();
        int maxDurability = itemStack.getMaxDamage();

        List<Text> tooltip = cir.getReturnValue();
        tooltip.add(Text.empty()); // Пустая строка для разделения.
        tooltip.add(Text.translatable("item.durability", currentDurability, maxDurability));

        cir.setReturnValue(tooltip);
    }

    @Unique
    private boolean shouldAddDurabilityTooltip(TooltipType type) {
        if (!SimpleConfig.client.enableHudDurabilityTooltip) return false;

        ItemStack itemStack = (ItemStack) (Object) this;
        return !type.isAdvanced() && itemStack.isDamageable();
    }
}
