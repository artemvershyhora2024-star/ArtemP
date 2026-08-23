package com.artempvp.hud.components;

import com.artempvp.hud.DraggableHudComponent;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;

public class KeybindsHud extends DraggableHudComponent {

    public KeybindsHud(int x, int y) {
        super(x, y, 140, 75); // Компактное окошко для биндов
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        MinecraftClient client = MinecraftClient.getInstance();

        // Темно-фиолетовый фон в стиле Syntax
        context.fill(x, y, x + width, y + height, 0xCC0B001A);

        // Заголовок панели
        context.drawText(client.textRenderer, "⌨ Keybinds", x + 8, y + 8, 0xFFFFFFFF, true);

        // Список биндов (название модуля / клавиша)
        context.drawText(client.textRenderer, "§7Hud", x + 8, y + 28, 0xFFD8BFD8, true);
        context.drawText(client.textRenderer, "§fRSHIFT", x + 105, y + 28, 0xFFBA55D3, true);

        context.drawText(client.textRenderer, "§7JumpWave", x + 8, y + 46, 0xFFD8BFD8, true);
        context.drawText(client.textRenderer, "§fALT", x + 120, y + 46, 0xFFBA55D3, true);
    }
}
