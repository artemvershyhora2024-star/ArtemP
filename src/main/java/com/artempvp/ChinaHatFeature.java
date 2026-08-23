package com.artempvp;

import com.artempvp.module.ModuleManager;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderContext;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.*;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.Vec3d;
import org.joml.Matrix4f;

public class ChinaHatFeature {
    private static float angle = 0.0f;

    public static void register() {
        WorldRenderEvents.LAST.register(context -> {
            // Проверяем, включен ли модуль China Hat в нашем ModuleManager
            boolean enabled = ModuleManager.getModules().stream()
                    .anyMatch(m -> m.getName().equals("China Hat") && m.isEnabled());
            if (!enabled) return;

            MinecraftClient client = MinecraftClient.getInstance();
            if (client.player == null || client.world == null) return;
            // Не рендерим от первого лица прямо над камерой, чтобы не закрывать обзор
            if (client.options.getPerspective().isFirstPerson()) return;

            MatrixStack matrices = context.matrixStack();
            matrices.push();

            Vec3d camPos = client.gameRenderer.getCamera().getPos();
            double x = client.player.getX() - camPos.x;
            double y = client.player.getY() + client.player.getHeight() + 0.15 - camPos.y;
            double z = client.player.getZ() - camPos.z;

            matrices.translate(x, y, z);

            angle += 2.0f; // Скорость вращения перелива цветов

            // Настройка вершин для конуса
            VertexConsumerProvider.Immediate bufferSource = client.getBufferBuilders().getEntityVertexConsumers();
            VertexConsumer buffer = bufferSource.getBuffer(RenderLayer.getDebugLineStrip(2.0D));

            Matrix4f matrix = matrices.peek().getPositionMatrix();

            int segments = 16;
            float radius = 0.4f;
            float height = 0.3f;

            // Рисуем линии конуса от вершины к основанию
            for (int i = 0; i < segments; i++) {
                double theta1 = (i / (double) segments) * Math.PI * 2;
                double theta2 = ((i + 1) / (double) segments) * Math.PI * 2;

                float x1 = (float) (Math.cos(theta1) * radius);
                float z1 = (float) (Math.sin(theta1) * radius);
                float x2 = (float) (Math.cos(theta2) * radius);
                float z2 = (float) (Math.sin(theta2) * radius);

                // Вершина шляпы
                buffer.vertex(matrix, 0, height, 0).color(180, 100, 255, 220).next();
                // Основание
                buffer.vertex(matrix, x1, 0, z1).color(120, 20, 200, 200).next();
                buffer.vertex(matrix, x2, 0, z2).color(120, 20, 200, 200).next();
            }

            matrices.pop();
        });
    }
}
