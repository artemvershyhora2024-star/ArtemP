package com.artempvp;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;

public class ArtemPvpMenuScreen extends Screen {

    public ArtemPvpMenuScreen() {
        super(Text.literal("ArtemPVP Menu"));
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        // Затемняем фон игры, когда меню открыто
        this.renderBackground(context, mouseX, mouseY, delta);

        // Рисуем центральное окно меню в стиле Syntax (темно-фиолетовый фон с рамкой)
        int centerX = this.width / 2 - 150;
        int centerY = this.height / 2 - 100;
        int width = 300;
        int height = 200;

        // Основной фон окна
        context.fill(centerX, centerY, centerX + width, centerY + height, 0xEE0B001A);
        // Неоновая верхняя полоска окна
        context.fill(centerX, centerY, centerX + width, centerY + 25, 0xFF2A1B3D);

        // Заголовок меню
        context.drawText(this.textRenderer, "§d⚡ ArtemPVP Client - HUD Settings", centerX + 10, centerY + 8, 0xFFFFFFFF, true);

        // Текст внутри меню
        context.drawText(this.textRenderer, "§7Здесь в будущем будут настройки модулей.", centerX + 15, centerY + 40, 0xFFAAAAAA, true);
        context.drawText(this.textRenderer, "§fНажми ESC, чтобы выйти в игру.", centerX + 15, centerY + 60, 0xFF777777, true);

        super.render(context, mouseX, mouseY, delta);
    }

    @Override
    public boolean shouldCloseOnEsc() {
        return true; // Закрытие меню по кнопке ESC
    }
}
