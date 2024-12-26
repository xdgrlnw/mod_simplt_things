package me.xdgrlnw.simple_things.mixin.client;

import me.xdgrlnw.simple_things.config.SimpleConfig;
import me.xdgrlnw.simple_things.config.SimpleConfigUtil;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.render.RenderTickCounter;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.GlobalPos;
import net.minecraft.world.LightType;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Optional;

@Mixin(InGameHud.class)
public abstract class SimpleHudToolsOverlay {

    @Shadow
    @Final
    private MinecraftClient client;

    @Inject(method = "render", at = @At("TAIL"))
    private void renderSimpleHud(DrawContext context, RenderTickCounter tickCounter, CallbackInfo ci) {
        if (!SimpleConfig.client.enableHudToolsOverlay || client.options.hudHidden) return;

        int yTopLeft = 8, yTopRight = 8;
        int xTopLeft = 8, xTopRight = client.getWindow().getScaledWidth() - 8;
        int yMargin = 14;

        if (holdItem(Items.COMPASS) || holdItem(Items.RECOVERY_COMPASS)) {
            drawHud(context, getPositionText(), xTopLeft, yTopLeft);
            yTopLeft += yMargin;

            if (holdItem(Items.RECOVERY_COMPASS) && getPlayer().getLastDeathPos().isPresent()) {
                drawHud(context, getDeathPositionText(), xTopLeft, yTopLeft);
                yTopLeft += yMargin;
            }
            drawHud(context, getBiomeText(), xTopLeft, yTopLeft);
        }
        if (holdItem(Items.CLOCK)) {
            drawHud(context, getDayTimeText(), xTopRight, yTopRight);
            yTopRight += yMargin;
        }
        if (holdItem(Items.DAYLIGHT_DETECTOR)) {
            drawHud(context, getLightText(), xTopRight, yTopRight);
        }
    }

    @Unique
    private void drawHud(DrawContext context, Text text, int xOffset, int yOffset) {
        int textWidth = client.textRenderer.getWidth(text);
        int screenWidth = client.getWindow().getScaledWidth();
        int screenHeight = client.getWindow().getScaledHeight();

        if (xOffset + textWidth > screenWidth) xOffset = screenWidth - textWidth - 10;
        if (yOffset + 10 > screenHeight) yOffset = screenHeight - 10 - 10;

        context.drawTextWithShadow(client.textRenderer, text, xOffset, yOffset,
                SimpleConfigUtil.getColorFromHex(SimpleConfig.client.hudOverlayColor));
    }

    @Unique
    private Text getPositionText() {
        if (!holdItem(Items.COMPASS) && !holdItem(Items.RECOVERY_COMPASS)) return Text.empty();
        PlayerEntity player = getPlayer();

        int x = player.getBlockX(), y = player.getBlockY(), z = player.getBlockZ();
        String[] directions = {"south", "south_west", "west", "north_west", "north", "north_east", "east", "south_east"};
        int index = Math.round((player.getYaw() % 360 + 360) % 360 / 45) % 8;
        Text facing = Text.translatable("text.facing." + directions[index]);

        return Text.translatable("text.hud.position", x, y, z, facing);
    }

    @Unique
    private Text getDeathPositionText() {
        if (!holdItem(Items.RECOVERY_COMPASS)) return Text.empty();
        PlayerEntity player = getPlayer();
        Optional<GlobalPos> lastDeathPos = player.getLastDeathPos();

        if (lastDeathPos.isEmpty()) return Text.empty();

        BlockPos deathPos = lastDeathPos.get().pos();
        int x = deathPos.getX(), y = deathPos.getY(), z = deathPos.getZ();

        if (player.squaredDistanceTo(x, y, z) < 4.0) player.setLastDeathPos(Optional.empty());

        return Text.translatable("text.hud.position.death", x, y, z);
    }

    @Unique
    private Text getBiomeText() {
        if (!holdItem(Items.COMPASS) && !holdItem(Items.RECOVERY_COMPASS)) return Text.empty();
        String biomeKey = getPlayer().getWorld().getBiome(getPlayer().getBlockPos()).getIdAsString();
        Text biomeTranslate = Text.translatable("biome." + biomeKey.replace(":", "."));

        return Text.translatable("text.hud.position.biome", biomeTranslate);
    }

    @Unique
    private Text getDayTimeText() {
        if (!holdItem(Items.CLOCK)) return Text.empty();
        long timeOfDay = getPlayer().getWorld().getTimeOfDay();
        long dayCount = timeOfDay / 24000;
        int hours = (int) ((timeOfDay % 24000) / 1000 + 6) % 24;
        int minutes = (int) ((timeOfDay % 1000) * 60 / 1000);

        String gameTime = String.format("%02d:%02d", hours, minutes);
        Text moonPhase = Text.translatable("text.moon_phase." + getPlayer().getWorld().getMoonPhase());

        return Text.translatable("text.hud.daytime", dayCount, moonPhase, gameTime);
    }

    @Unique
    private Text getLightText() {
        if (!holdItem(Items.DAYLIGHT_DETECTOR)) return Text.empty();

        int lightBlock = getPlayer().getWorld().getLightLevel(LightType.BLOCK, getPlayer().getBlockPos());
        int lightSky = getPlayer().getWorld().getLightLevel(LightType.SKY, getPlayer().getBlockPos());

        return Text.translatable("text.hud.light", lightBlock, lightSky);
    }

    @Unique
    private boolean holdItem(Item item) {
        return getPlayer().getMainHandStack().isOf(item) || getPlayer().getOffHandStack().isOf(item);
    }

    @Unique
    private PlayerEntity getPlayer() {
        if (client.player == null) {
            throw new IllegalStateException("Player is null!");
        }
        return client.player;
    }
}
