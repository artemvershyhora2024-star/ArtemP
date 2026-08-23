package com.artempvp;

import com.artempvp.hud.DraggableHudComponent;
import com.artempvp.hud.HudManager;
import com.artempvp.module.Module;
import com.artempvp.module.ModuleManager;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;

import java.util.List;

public class ArtemPvpMenuScreen extends Screen {
    public static boolean musicHudEnabled = true;
    public static boolean cooldownsHudEnabled = true;

    private Module.Category currentCategory = Module.Category.VISUALS;
    private DraggableHudComponent selectedComponent = null;
    private int dragOffsetX = 0;
    private int dragOffsetY = 0;

    public ArtemPvpMenuScreen() {
        super(Text.literal("ArtemPVP Menu"));
    }

    @Override
    protected void init() {
        super.init();
        rebuildWidgets();
    }

    private void rebuildWidgets() {
        this.clearChildren();

        int centerX = this.width / 2 - 175;
        int centerY = this.height / 2 - 120;

        // Кнопки выбора категорий сверху панели
        this.addDrawableChild(ButtonWidget.builder(Text.literal("§dВизуальные"), b -> { currentCategory = Module.Category.VISUALS; rebuildWidgets(); })
                .dimensions(centerX + 20, centerY + 35, 100, 18).build());
        this.addDrawableChild(ButtonWidget.builder(Text.literal("§dУтилиты"), b -> { currentCategory = Module.Category.UTILITIES; rebuildWidgets(); })
                .dimensions(centerX + 125, centerY + 35, 100, 18).build());
        this.addDrawableChild(ButtonWidget.builder(Text.literal("§dHUD элементы"), b -> { currentCategory = Module.Category.HUD; rebuildWidgets(); })
                .dimensions(centerX + 230, centerY + 35, 100, 18).build());

        // Динамический вывод модулей выбранной категории в виде аккуратного списка
        List<Module> categoryModules = ModuleManager.getModulesByCategory(currentCategory);
        int startY = centerY + 65;
        for (int i = 0; i < categoryModules.size(); i++) {
            Module mod = categoryModules.get(i);
            int x = (i % 2 == 0) ? centerX + 20 : centerX + 185;
            int y = startY + (i / 2) * 24;

            String status = mod.isEnabled() ? "§aВКЛ" : "§cВЫКЛ";
            this.addDrawableChild(ButtonWidget.builder(Text.literal(mod.getName() + ": " + status), button -> {
                mod.toggle();
                button.setMessage(Text.literal(mod.getName() + ": " + (mod.isEnabled() ? "§aВКЛ" : "§cВЫКЛ")));
                ArtemPvpConfig.save();
            }).dimensions(x, y, 160, 20).build());
        }
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        // Рендерим HUD элементы на заднем фоне для перетаскивания мышкой
        for (DraggableHudComponent comp : HudManager.getComponents()) {
            comp.render(context, mouseX, mouseY, delta);
            context.drawBorder(comp.getX(), comp.getY(), comp.getWidth(), comp.getHeight(), 0x777B2CBF);
        }

        int centerX = this.width / 2 - 175;
        int centerY = this.height / 2 - 120;

        // Окно клиентского меню
        context.fill(centerX, centerY, centerX + 350, centerY + 240, 0xEE0B001A);
        context.fill(centerX, centerY, centerX + 350, centerY + 28, 0xFF2A1B3D);
        context.drawText(this.textRenderer, "§d⚡ ArtemPVP Client — Модули и Настройки", centerX + 12, centerY + 9, 0xFFFFFFFF, true);

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
            ArtemPvpConfig.save();
        }
        return super.mouseReleased(mouseX, mouseY, button);
    }

    @Override
    public boolean shouldCloseOnEsc() {
        return true;
    }
}
