package com.artempvp.hud.components;

import com.artempvp.hud.DraggableHudComponent;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;

public class PotionsHud extends DraggableHudComponent {

    public PotionsHud(int x, int y) {
        super(x, y, 140, 110); // Размер окошка под зелья
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        MinecraftClient client = MinecraftClient.getInstance();

        // Полупрозрачный темно-фиолетовый фон с неоновой рамкой
        context.fill(x, y, x + width, y + height, 0xCC0B001A);
        
        // Заголовок панели
        context.drawText(client.textRenderer, "§5⚗ Potions", x + 8, y + 8, 0xFFFFFFFF, true);
        
        // Пример отображения активных эффектов (заглушка под реальные эффекты игрока)
        context.drawText(client.textRenderer, "§c⚡ Сила III", x + 8, y + 30, 0xFFFF5555, true);
        context.drawText(client.textRenderer, "3:14", x + 105, y + 30, 0xFFAAAAAA, true);

        context.drawText(client.textRenderer, "§b🛡 Скорость II", x + 8, y + 50, 0xFF55FFFF, true);
        context.drawText(client.textRenderer, "1:45", x + 105, y + 50, 0xFFAAAAAA, true);

        context.drawText(client.textRenderer, "§6🔥 Огнестойкость", x + 8, y + 70, 0xFFFFAA00, true);
        context.drawText(client.textRenderer, "5:00", x + 105, y + 70, 0xFFAAAAAA, true);
    }
}
