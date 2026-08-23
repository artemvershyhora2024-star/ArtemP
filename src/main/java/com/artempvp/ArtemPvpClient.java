package com.artempvp;

import com.artempvp.hud.HudManager;
import com.artempvp.hud.components.*;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ArtemPvpClient implements ClientModInitializer {
    public static final String MOD_ID = "artempvp-client";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    public static TopBarHud topBarHud;
    public static PotionsHud potionsHud;
    public static MusicPlayerHud musicPlayerHud;
    public static CooldownsHud cooldownsHud;
    public static KeybindsHud keybindsHud;
    public static InventoryHud inventoryHud;
    public static TargetHud targetHud;
    public static KeystrokesHud keystrokesHud; // Наша новая переменная Keystrokes

    @Override
    public void onInitializeClient() {
        LOGGER.info("ArtemPVP Client fully initialized with Keystrokes!");

        KeyBindingManager.registerKeys();

        // Инициализируем координаты всех элементов HUD
        topBarHud = new TopBarHud(10, 10);
        cooldownsHud = new CooldownsHud(10, 40);
        keybindsHud = new KeybindsHud(10, 150);
        potionsHud = new PotionsHud(180, 10);
        musicPlayerHud = new MusicPlayerHud(330, 10);
        inventoryHud = new InventoryHud(330, 95);
        targetHud = new TargetHud(330, 175);
        keystrokesHud = new KeystrokesHud(10, 235); // Размещаем Keystrokes под остальными панелями слева

        // Рендеринг элементов с учетом состояния кнопок в меню
        HudRenderCallback.EVENT.register((drawContext, tickDelta) -> {
            ArtemPvpClient.topBarHud.render(drawContext, 0, 0, tickDelta);
            ArtemPvpClient.potionsHud.render(drawContext, 0, 0, tickDelta);
            ArtemPvpClient.keybindsHud.render(drawContext, 0, 0, tickDelta);
            ArtemPvpClient.inventoryHud.render(drawContext, 0, 0, tickDelta);
            ArtemPvpClient.targetHud.render(drawContext, 0, 0, tickDelta);
            ArtemPvpClient.keystrokesHud.render(drawContext, 0, 0, tickDelta); // Рендерим WASD

            if (ArtemPvpMenuScreen.musicHudEnabled) {
                ArtemPvpClient.musicPlayerHud.render(drawContext, 0, 0, tickDelta);
            }
            if (ArtemPvpMenuScreen.cooldownsHudEnabled) {
                ArtemPvpClient.cooldownsHud.render(drawContext, 0, 0, tickDelta);
            }
        });

        // Открытие GUI по нажатию Right Shift
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (KeyBindingManager.openMenuKey.wasPressed()) {
                if (client.player != null) {
                    client.setScreen(new ArtemPvpMenuScreen());
                }
            }
        });
    }
}
