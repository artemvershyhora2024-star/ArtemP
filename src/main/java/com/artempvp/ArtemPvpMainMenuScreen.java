package com.artempvp;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.multiplayer.MultiplayerScreen;
import net.minecraft.client.gui.screen.options.OptionsScreen;
import net.minecraft.client.gui.screen.world.SelectWorldScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;

public class ArtemPvpMainMenuScreen extends Screen {
    private float animationTime = 0.0f;

    public ArtemPvpMainMenuScreen() {
        super(Text.literal("ArtemPVP Main Menu"));
    }

    @Override
    protected void init() {
        super.init();

        int buttonWidth = 200;
        int buttonHeight = 20;
        int centerX = this.width / 2 - buttonWidth / 2;
        int startY = this.height / 2 + 10;

        // Кнопка одиночной игры
        this.addDrawableChild(ButtonWidget.builder(Text.literal("⚡ Одиночная игра"), button -> {
            this.client.setScreen(new SelectWorldScreen(this));
        }).dimensions(centerX, startY, buttonWidth, buttonHeight).build());

        // Кнопка сетевой игры
        this.addDrawableChild(ButtonWidget.builder(Text.literal("⚔ Сетевая игра"), button -> {
            this.client.setScreen(new MultiplayerScreen(this));
        }).dimensions(centerX, startY + 24, buttonWidth, buttonHeight).build());

        // Кнопка настроек
        this.addDrawableChild(ButtonWidget.builder(Text.literal("⚙ Настройки"), button -> {
            this.client.setScreen(new OptionsScreen(this, this.client.options));
        }).dimensions(centerX, startY + 48, buttonWidth, buttonHeight).build());

        // Кнопка выхода
        this.addDrawableChild(ButtonWidget.builder(Text.literal("❌ Выйти из игры"), button -> {
            this.client.scheduleStop();
        }).dimensions(centerX, startY + 72, buttonWidth, buttonHeight).build());
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        // Увеличиваем время для плавной анимации перелива
        animationTime += delta * 0.05f;
        
        // Математически вычисляем пульсацию цвета (перелив от темного к неоново-фиолетовому)
        float wave = (float)(Math.sin(animationTime) + 1.0f) / 2.0f; // от 0 до 1
        int dynamicPurple = blendColors(0xFF150024, 0xFF5A189A, wave);
        int secondaryPurple = blendColors(0xFF0B001A, 0xFF7B2CBF, 1.0f - wave);

        // Рисуем градиентный фон (сверху темный, по центру переливающийся фиолетовый)
        context.fillGradient(0, 0, this.width, this.height, 0xFF05000A, dynamicPurple);

        // --- Авторский логотип ArtemPVP ---
        int logoCenterX = this.width / 2;
        int logoY = this.height / 4 - 10;

        // Рисуем неоновую подложку под логотип для свечения
        context.fill(logoCenterX - 130, logoY - 15, logoCenterX + 130, logoY + 45, 0x337B2CBF);
        
        // Рамка логотипа в стиле Syntax
        context.drawBorder(logoCenterX - 130, logoY - 15, 260, 60, secondaryPurple);

        // Текст логотипа
        String mainLogo = "A R T E M P V P";
        String subLogo = "PREMIUM PVP CLIENT";
        
        int mainWidth = this.textRenderer.getWidth(mainLogo);
        int subWidth = this.textRenderer.getWidth(subLogo);

        // Тень и сам логотип
        context.drawText(this.textRenderer, mainLogo, logoCenterX - (mainWidth / 2) + 1, logoY + 2 + 1, 0xFF000000, false);
        context.drawText(this.textRenderer, mainLogo, logoCenterX - (mainWidth / 2), logoY + 2, 0xFFE0AAFF, true);

        // Подзаголовок
        context.drawText(this.textRenderer, subLogo, logoCenterX - (subWidth / 2), logoY + 22, 0xFFAAAAAA, true);

        super.render(context, mouseX, mouseY, delta);
    }

    // Вспомогательный метод для плавного смешивания цветов градиента
    private int blendColors(int color1, int color2, float ratio) {
        int a = (int)ើ((color1 >> 24 & 0xFF) * (1 - ratio) + (color2 >> 24 & 0xFF) * ratio);
        int r = (int)((color1 >> 16 & 0xFF) * (1 - ratio) + (color2 >> 16 & 0xFF) * ratio);
        int g = (int)((color1 >> 8 & 0xFF) * (1 - ratio) + (color2 >> 8 & 0xFF) * ratio);
        int b = (int)((color1 & 0xFF) * (1 - ratio) + (color2 & 0xFF) * ratio);
        return (a << 24) | (r << 16) | (g << 8) | b;
    }

    @Override
    public boolean shouldCloseOnEsc() {
        return false;
    }
}
