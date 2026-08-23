package com.artempvp;

import com.artempvp.hud.DraggableHudComponent;
import com.artempvp.hud.HudManager;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;

public class ArtemPvpMenuScreen extends Screen {
    public static boolean musicHudEnabled = true;
    public static boolean cooldownsHudEnabled = true;

    private DraggableHudComponent selectedComponent = null;
    private int dragOffsetX = 0;
    private int dragOffsetY = 0;

    public ArtemPvpMenuScreen() {
        super(Text.literal("ArtemPVP Menu"));
    }

    @Override
    protected void init() {
        super.init();

        int centerX = this.width / 2 - 150;
        int centerY = this.height / 2 - 100;

        // Левая колонка — HUD элементы и базовые функции
        this.addDrawableChild(ButtonWidget.builder(
                Text.literal(musicHudEnabled ? "§aПлеер: ВКЛ" : "§cПлеер: ВЫКЛ"),
                button -> {
                    musicHudEnabled = !musicHudEnabled;
                    button.setMessage(Text.literal(musicHudEnabled ? "§aПлеер: ВКЛ" : "§cПлеер: ВЫКЛ"));
                    ArtemPvpConfig.save();
                })
                .dimensions(centerX + 20, centerY + 50, 120, 20)
                .build());

        this.addDrawableChild(ButtonWidget.builder(
                Text.literal(cooldownsHudEnabled ? "§aКулдауны: ВКЛ" : "§cКулдауны: ВЫКЛ"),
                button -> {
                    cooldownsHudEnabled = !cooldownsHudEnabled;
                    button.setMessage(Text.literal(cooldownsHudEnabled ? "§aКулдауны: ВКЛ" : "§cКулдауны: ВЫКЛ"));
                    ArtemPvpConfig.save();
                })
                .dimensions(centerX + 20, centerY + 80, 120, 20)
                .build());

        this.addDrawableChild(ButtonWidget.builder(
                Text.literal(ArtemPvpUtilities.fullbrightEnabled ? "§aFullbright: ВКЛ" : "§cFullbright: ВЫКЛ"),
                button -> {
                    ArtemPvpUtilities.toggleFullbright();
                    button.setMessage(Text.literal(ArtemPvpUtilities.fullbrightEnabled ? "§aFullbright: ВКЛ" : "§cFullbright: ВЫКЛ"));
                })
                .dimensions(centerX + 20, centerY + 110, 120, 20)
                .build());

        this.addDrawableChild(ButtonWidget.builder(
                Text.literal(ArtemPvpUtilities.autoSprintEnabled ? "§aАвто-Спринт: ВКЛ" : "§cАвто-Спринт: ВЫКЛ"),
                button -> {
                    ArtemPvpUtilities.autoSprintEnabled = !ArtemPvpUtilities.autoSprintEnabled;
                    button.setMessage(Text.literal(ArtemPvpUtilities.autoSprintEnabled ? "§aАвто-Спринт: ВКЛ" : "§cАвто-Спринт: ВЫКЛ"));
                })
                .dimensions(centerX + 20, centerY + 140, 120, 20)
                .build());

        // Правая колонка — новые PvP утилиты
        this.addDrawableChild(ButtonWidget.builder(
                Text.literal(ArtemPvpPvPUtilities.autoRespawnEnabled ? "§aАвто-Респавн: ВКЛ" : "§cАвто-Респавн: ВЫКЛ"),
                button -> {
                    ArtemPvpPvPUtilities.autoRespawnEnabled = !ArtemPvpPvPUtilities.autoRespawnEnabled;
                    button.setMessage(Text.literal(ArtemPvpPvPUtilities.autoRespawnEnabled ? "§aАвто-Респавн: ВКЛ" : "§cАвто-Респавн: ВЫКЛ"));
                })
                .dimensions(centerX + 160, centerY + 50, 120, 20)
                .build());

        this.addDrawableChild(ButtonWidget.builder(
                Text.literal(ArtemPvpPvPUtilities.blockOverlayEnabled ? "§aБлок-Оверлей: ВКЛ" : "§cБлок-Оверлей: ВЫКЛ"),
                button -> {
                    ArtemPvpPvPUtilities.blockOverlayEnabled = !ArtemPvpPvPUtilities.blockOverlayEnabled;
                    button.setMessage(Text.literal(ArtemPvpPvPUtilities.blockOverlayEnabled ? "§aБлок-Оверлей: ВКЛ" : "§cБлок-Оверлей: ВЫКЛ"));
                })
                .dimensions(centerX + 160, centerY + 80, 120, 20)
                .build());
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        for (DraggableHudComponent comp : HudManager.getComponents()) {
            comp.render(context, mouseX, mouseY, delta);
            context.drawBorder(comp.getX(), comp.getY(), comp.getWidth(), comp.getHeight(), 0x777B2CBF);
        }

        int centerX = this.width / 2 - 150;
        int centerY = this.height / 2 - 100;
        
        context.fill(centerX, centerY, centerX + 300, centerY + 200, 0xEE0B001A);
        context.fill(centerX, centerY, centerX + 300, centerY + 25, 0xFF2A1B3D);
        context.drawText(this.textRenderer, "§d⚡ ArtemPVP Client - Настройки", centerX + 10, centerY + 8, 0xFFFFFFFF, true);

        super.render(context, mouseX, mouseY, delta);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (button == 0) {
            for (DraggableHudComponent comp : HudManager.getComponents()) {
                if (mouseX >= comp.getX() && mouseX <= comp.getX() + comp.getWidth() &&
                    mouseY >= comp.getY() && mouseY <= comp.getY() + comp.getHeight()) {
                    selectedComponent = comp;
                    dragOffsetX = (int) mouseX - comp.getX();
                    dragOffsetY = (int) mouseY - comp.getY();
                    return true;
                }
            }
        }
        return super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public boolean mouseDragged(double mouseX, double mouseY, int button, double deltaX, double deltaY) {
        if (selectedComponent != null && button == 0) {
            selectedComponent.setX((int) mouseX - dragOffsetX);
            selectedComponent.setY((int) mouseY - dragOffsetY);
            return true;
        }
        return super.mouseDragged(mouseX, mouseY, button, deltaX, deltaY);
    }

    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int button) {
        if (button == 0) {
            selectedComponent = null;
            ArtemPvpConfig.save(); // Сохраняем координаты при отпускании мышки
        }
        return super.mouseReleased(mouseX, mouseY, button);
    }

    @Override
    public boolean shouldCloseOnEsc() {
        return true;
    }
}
