package com.artempvp;

import com.artempvp.hud.components.TopBarHud;
import com.artempvp.hud.components.PotionsHud;
import com.artempvp.hud.components.MusicPlayerHud;
import com.artempvp.hud.components.CooldownsHud;
import com.artempvp.hud.components.KeybindsHud;
import com.artempvp.hud.components.InventoryHud;
import net.fabricmc.api.ClientModInitializer;
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

    @Override
    public void onInitializeClient() {
        LOGGER.info("ArtemPVP Client initialized!");

        // Инициализируем все окна интерфейса
        topBarHud = new TopBarHud(10, 10);
        cooldownsHud = new CooldownsHud(10, 40);
        keybindsHud = new KeybindsHud(10, 150);
        potionsHud = new PotionsHud(180, 10);
        musicPlayerHud = new MusicPlayerHud(330, 10);
        inventoryHud = new InventoryHud(330, 95);

        // Регистрируем их общую отрисовку на экране
        HudRenderCallback.EVENT.register((drawContext, tickDelta) -> {
            topBarHud.render(drawContext, 0, 0, tickDelta);
            cooldownsHud.render(drawContext, 0, 0, tickDelta);
            keybindsHud.render(drawContext, 0, 0, tickDelta);
            potionsHud.render(drawContext, 0, 0, tickDelta);
            musicPlayerHud.render(drawContext, 0, 0, tickDelta);
            inventoryHud.render(drawContext, 0, 0, tickDelta);
        });
    }
}
