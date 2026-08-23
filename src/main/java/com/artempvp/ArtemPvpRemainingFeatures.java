package com.artempvp;

import com.artempvp.module.ModuleManager;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.Box;

public class ArtemPvpRemainingFeatures {

    public static void register() {
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (client.player == null || client.world == null) return;

            // 1. Fast EXP (ускоряем подбор опыта, сбрасывая задержку подбора)
            boolean fastExpEnabled = ModuleManager.getModules().stream().anyMatch(m -> m.getName().equals("Fast EXP") && m.isEnabled());
            if (fastExpEnabled) {
                // Убираем кулдаун подбора сферы опыта у игрока
                client.player.pickupDelay = 0;
            }

            // 2. Healing Helper (автоматическое предупреждение или помощь при низком хр)
            boolean healingEnabled = ModuleManager.getModules().stream().anyMatch(m -> m.getName().equals("Healing Helper") && m.isEnabled());
            if (healingEnabled && client.player.getHealth() <= 6.0f) {
                // Можно добавить логику или просто выводить подсказку
            }

            // 3. Ambience (вечная ночь или кастомное время)
            boolean ambienceEnabled = ModuleManager.getModules().stream().anyMatch(m -> m.getName().equals("Ambience") && m.isEnabled());
            if (ambienceEnabled) {
                client.world.setTimeOfDay(18000L); // Фиксируем ночь/закат
            }
        });

        // 4. Target ESP (подсветка игрока, на которого смотришь)
        WorldRenderEvents.LAST.register(context -> {
            boolean targetEspEnabled = ModuleManager.getModules().stream().anyMatch(m -> m.getName().equals("Target ESP") && m.isEnabled());
            if (!targetEspEnabled) return;

            MinecraftClient client = MinecraftClient.getInstance();
            if (client.player == null || client.world == null) return;

            HitResult hit = client.crosshairTarget;
            if (hit != null && hit.getType() == HitResult.Type.ENTITY) {
                EntityHitResult entityHit = (EntityHitResult) hit;
                Entity target = entityHit.getEntity();

                if (target instanceof PlayerEntity && target != client.player) {
                    MatrixStack matrices = context.matrixStack();
                    matrices.push();

                    double camX = client.gameRenderer.getCamera().getPos().x;
                    double camY = client.gameRenderer.getCamera().getPos().y;
                    double camZ = client.gameRenderer.getCamera().getPos().z;

                    matrices.translate(target.getX() - camX, target.getY() - camY, target.getZ() - camZ);

                    Box box = target.getBoundingBox().offset(-target.getX(), -target.getY(), -target.getZ());
                    WorldRenderer.drawBox(matrices, context.consumers().getBuffer(RenderLayer.getLines()), box, 1.0f, 0.2f, 1.0f, 1.0f);

                    matrices.pop();
                }
            }
        });
    }
}
