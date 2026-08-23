package com.artempvp;

import com.artempvp.hud.components.*;
import com.artempvp.module.ModuleManager;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.fabricmc.fabric.api.client.screen.v1.ScreenEvents;
import net.minecraft.client.gui.screen.TitleScreen;
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
    public static KeystrokesHud keystrokesHud;

    @Override
    public void onInitializeClient() {
        LOGGER.info("ArtemPVP Client fully loaded with ALL features!");

        ArtemPvpConfig.load();
        KeyBindingManager.registerKeys();

        // Регистрируем абсолютно все системы и модули
        ModuleManager.init();
        ArtemPvpUtilities.registerUtilities();
        ArtemPvpPvPUtilities.register();
        ArtemPvpVisualAndPvP2.register();
        ChinaHatFeature.register();
        AspectRatioMixinHelper.register();
        ArtemPvpRemainingFeatures.register(); // Все оставшиеся функции

        topBarHud = new TopBarHud(ArtemPvpConfig.DATA.topBarX, ArtemPvpConfig.DATA.topBarY);
        cooldownsHud = new CooldownsHud(ArtemPvpConfig.DATA.cooldownsX, ArtemPvpConfig.DATA.cooldownsY);
        keybindsHud = new KeybindsHud(ArtemPvpConfig.DATA.keybindsX, ArtemPvpConfig.DATA.keybindsY);
        potionsHud = new PotionsHud(ArtemPvpConfig.DATA.potionsX, ArtemPvpConfig.DATA.potionsY);
        musicPlayerHud = new MusicPlayerHud(ArtemPvpConfig.DATA.musicPlayerX, ArtemPvpConfig.DATA.musicPlayerY);
        inventoryHud = new InventoryHud(ArtemPvpConfig.DATA.inventoryX, ArtemPvpConfig.DATA.inventoryY);
        targetHud = new TargetHud(ArtemPvpConfig.DATA.targetX, ArtemPvpConfig.DATA.targetY);
        keystrokesHud = new KeystrokesHud(ArtemPvpConfig.DATA.keystrokesX, ArtemPvpConfig.DATA.keystrokesY);

        ArtemPvpMenuScreen.musicHudEnabled = ArtemPvpConfig.DATA.musicHudEnabled;
        ArtemPvpMenuScreen.cooldownsHudEnabled = ArtemPvpConfig.DATA.cooldownsHudEnabled;

        HudRenderCallback.EVENT.register((drawContext, tickDelta) -> {
            ArtemPvpClient.topBarHud.render(drawContext, 0, 0, tickDelta);
            ArtemPvpClient.potionsHud.render(drawContext, 0, 0, tickDelta);
            ArtemPvpClient.keybindsHud.render(drawContext, 0, 0, tickDelta);
            ArtemPvpClient.inventoryHud.render(drawContext, 0, 0, tickDelta);
            ArtemPvpClient.targetHud.render(drawContext, 0, 0, tickDelta);
            ArtemPvpClient.keystrokesHud.render(drawContext, 0, 0, tickDelta);

            if (ArtemPvpMenuScreen.musicHudEnabled) {
                ArtemPvpClient.musicPlayerHud.render(drawContext, 0, 0, tickDelta);
            }
            if (ArtemPvpMenuScreen.cooldownsHudEnabled) {
                ArtemPvpClient.cooldownsHud.render(drawContext, 0, 0, tickDelta);
            }
        });

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (KeyBindingManager.openMenuKey.wasPressed()) {
                if (client.player != null) {
                    client.setScreen(new ArtemPvpMenuScreen());
                }
            }
        });

        ScreenEvents.AFTER_INIT.register((client, screen, scaledWidth, scaledHeight) -> {
            if (screen instanceof TitleScreen && !(screen instanceof ArtemPvpMainMenuScreen)) {
                client.setScreen(new ArtemPvpMainMenuScreen());
            }
        });
    }
}
