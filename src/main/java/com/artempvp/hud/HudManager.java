package com.artempvp.hud;

import com.artempvp.ArtemPvpClient;
import java.util.Arrays;
import java.util.List;

public class HudManager {
    public static List<DraggableHudComponent> getComponents() {
        return Arrays.asList(
            ArtemPvpClient.topBarHud,
            ArtemPvpClient.cooldownsHud,
            ArtemPvpClient.keybindsHud,
            ArtemPvpClient.potionsHud,
            ArtemPvpClient.musicPlayerHud,
            ArtemPvpClient.inventoryHud,
            ArtemPvpClient.targetHud,
            ArtemPvpClient.keystrokesHud
        );
    }
}
