package com.artempvp;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;

public class ArtemPvpMenuScreen extends Screen {
    // Состояние включения/выключения для наших HUD модулей
    public static boolean musicHudEnabled = true;
    public static boolean cooldownsHudEnabled = true;

    public ArtemPvpMenuScreen() {
        super(Text.literal("ArtemPVP Menu"));
    }

    @Override
    protected void init() {
        super.init();

        int centerX = this.width / 2 - 150;
        int centerY = this.height / 2 - 100;

        // Кнопка управления плеером
        this.addDrawableChild(ButtonWidget.builder(
                Text.literal(musicHudEnabled ? "§aПлеер: ВКЛ" : "§cПлеер: ВЫКЛ"),
                button -> {
                    musicHudEnabled = !musicHudEnabled;
                    button.setMessage(Text.literal(musicHudEnabled ? "§aПлеер: ВКЛ" : "§cПлеер: ВЫКЛ"));
                })
                .dimensions(centerX + 20, centerY + 50, 120, 20)
                .build());

        // Кнопка управления кулдаунами
        this.addDrawableChild(ButtonWidget.builder(
                Text.literal(cooldownsHudEnabled ? "§aКулдауны: ВКЛ" : "§cКулдауны: ВЫКЛ"),
                button -> {
                    cooldownsHudEnabled = !cooldownsHudEnabled;
                    button.setMessage(Text.literal(cooldownsHudEnabled ? "§aКулдауны: ВКЛ" : "§cКулдауны: ВЫКЛ"));
                })
                .dimensions(centerX + 20, centerY + 80, 120, 20)
                .build());
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        this.renderBackground(context, mouseX, mouseY, delta);

        int centerX = this.width / 2 - 150;
        int centerY = this.height / 2 - 100;
        int width = 300;
        int height = 200;

        // Основное неоново-фиолетовое окно
        context.fill(centerX, centerY, centerX + width, centerY + height, 0xEE0B001A);
        context.fill(centerX, centerY, centerX + width, centerY + 25, 0xFF2A1B3D);

        // Заголовок
        context.drawText(this.textRenderer, "§d⚡ ArtemPVP Client - Управление модулями", centerX + 10, centerY + 8, 0xFFFFFFFF, true);

        super.render(context, mouseX, mouseY, delta);
    }

    @Override
    public boolean shouldCloseOnEsc() {
        return true;
    }
}
