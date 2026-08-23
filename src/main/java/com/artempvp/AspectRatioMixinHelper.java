package com.artempvp;

import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderContext;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderEvents;

public class AspectRatioMixinHelper {
    public static void register() {
        // Мы можем отслеживать состояние и плавно применять растягивание матрицы проекции
        WorldRenderEvents.START.register(context -> {
            boolean enabled = ModuleManager.getModules().stream()
                    .anyMatch(m -> m.getName().equals("Aspect Ratio") && m.isEnabled());
            
            if (enabled && context.matrixStack() != null) {
                // Применяем масштаб по оси X для визуального расширения мира
                context.matrixStack().scale(1.25f, 1.0f, 1.0f);
            }
        });
    }
}
