package com.artempvp;

import com.artempvp.hud.HudManager;
import com.artempvp.hud.components.*;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ArtemPvpClient implements ClientModInitializer {
    public static final String MOD_ID = "artempvp-client";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    // Объявляем все компоненты HUD
    public static TopBarHud topBarHud;
    public static PotionsHud potionsHud;
    public static MusicPlayerHud musicPlayerHud;
    public static CooldownsHud cooldownsHud;
    public static KeybindsHud keybindsHud;
    public static InventoryHud inventoryHud;
    public static TargetHud targetHud;

    @Override
    public void onInitializeClient() {
        LOGGER.info("ArtemPVP Client fully initialized with all PvP modules!");

        // Задаем начальные координаты для каждого окошка на экране
        topBarHud = new TopBarHud(10, 10);
        cooldownsHud = new CooldownsHud(10, 40);
        keybindsHud = new KeybindsHud(10, 150);
        potionsHud = new PotionsHud(180, 10);
        musicPlayerHud = new MusicPlayerHud(330, 10);
        inventoryHud = new InventoryHud(330, 95);
        targetHud = new TargetHud(330, 175);

        // Регистрируем отрисовку всех элементов интерфейса через менеджер
        HudRenderCallback.EVENT.register((drawContext, tickDelta) -> {
            for (var comp : HudManager.getComponents()) {
                comp.render(drawContext, 0, 0, tickDelta);
            }
        });
    }
}
