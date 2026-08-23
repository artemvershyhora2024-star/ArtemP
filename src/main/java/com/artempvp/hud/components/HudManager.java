package com.artempvp.hud;

import com.artempvp.ArtemPvpClient;
import java.util.Arrays;
import java.util.List;

public class HudManager {
    // Список всех активных компонентов интерфейса
    public static List<DraggableHudComponent> getComponents() {
        return Arrays.asList(
            ArtemPvpClient.topBarHud,
            ArtemPvpClient.cooldownsHud,
            ArtemPvpClient.keybindsHud,
            ArtemPvpClient.potionsHud,
            ArtemPvpClient.musicPlayerHud,
            ArtemPvpClient.inventoryHud
        );
    }

    public static void mouseClicked(double mouseX, double mouseY, int button) {
        for (DraggableHudComponent comp : getComponents()) {
            comp.mouseClicked(mouseX, mouseY, button);
        }
    }

    public static void mouseReleased(double mouseX, double mouseY, int button) {
        for (DraggableHudComponent comp : getComponents()) {
            comp.mouseReleased(mouseX, mouseY, button);
        }
    }

    public static void mouseDragged(double mouseX, double mouseY, int button, double deltaX, double deltaY) {
        for (DraggableHudComponent comp : getComponents()) {
            comp.mouseDragged(mouseX, mouseY, button, deltaX, deltaY);
        }
    }
}
