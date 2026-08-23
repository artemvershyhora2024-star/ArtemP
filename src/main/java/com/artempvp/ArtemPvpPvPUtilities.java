package com.artempvp;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderContext;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.client.render.WorldRenderer;

public class ArtemPvpPvPUtilities {
    public static boolean autoRespawnEnabled = true;
    public static boolean deathCoordinatesEnabled = true;
    public static boolean blockOverlayEnabled = true;

    private static boolean wasDead = false;

    public static void register() {
        // 1. Авто-респавн и Координаты смерти
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (client.player == null) return;

            // Проверка на смерть
            boolean isDead = client.player.isDead() || client.player.getHealth() <= 0.0f;

            if (isDead && !wasDead) {
                // Игрок только что умер - сохраняем координаты смерти
                if (deathCoordinatesEnabled) {
                    int deathX = (int) client.player.getX();
                    int deathY = (int) client.player.getY();
                    int deathZ = (int) client.player.getZ();
                    client.player.sendMessage(Text.literal("§5[ArtemPVP] §cТы умер на координатах: §eX: " + deathX + ", Y: " + deathY + ", Z: " + deathZ), false);
                }
            }

            if (isDead && autoRespawnEnabled) {
                client.player.requestRespawn();
            }

            wasDead = isDead;
        });

        // 2. Block Overlay (Подсветка блока, на который смотрит игрок)
        WorldRenderEvents.LAST.register(context -> {
            if (!blockOverlayEnabled) return;
            MinecraftClient client = MinecraftClient.getInstance();
            if (client.player == null || client.world == null) return;

            HitResult hit = client.crosshairTarget;
            if (hit != null && hit.getType() == HitResult.Type.BLOCK) {
                BlockHitResult blockHit = (BlockHitResult) hit;
                BlockPos pos = blockHit.getBlockPos();

                MatrixStack matrices = context.matrixStack();
                matrices.push();

                // Смещаем относительно камеры мира
                double camX = client.gameRenderer.getCamera().getPos().x;
                double camY = client.gameRenderer.getCamera().getPos().y;
                double camZ = client.gameRenderer.getCamera().getPos().z;

                matrices.translate(pos.getX() - camX, pos.getY() - camY, pos.getZ() - camZ);

                Box box = new Box(0, 0, 0, 1, 1, 1);
                
                // Рисуем неоново-фиолетовую обводку блока
                WorldRenderer.drawBox(matrices, context.consumers().getBuffer(RenderLayer.getLines()), box, 0.49f, 0.17f, 0.75f, 1.0f);

                matrices.pop();
            }
        });
    }
}
