package com.artempvp;

import com.artempvp.hud.components.TopBarHud;
import com.artempvp.hud.components.PotionsHud;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ArtemPvpClient implements ClientModInitializer {
    public static final String MOD_ID = "artempvp-client";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    public static TopBarHud topBarHud;
    public static PotionsHud potionsHud;

    @Override
    public void onInitializeClient() {
        LOGGER.info("ArtemPVP Client initialized!");

        // Инициализируем элементы HUD на экране
        topBarHud = new TopBarHud(10, 10);
        potionsHud = new PotionsHud(200, 10); // Расположим правее верхней панели

        // Регистрируем отрисовку
        HudRenderCallback.EVENT.register((drawContext, tickDelta) -> {
            topBarHud.render(drawContext, 0, 0, tickDelta);
            potionsHud.render(drawContext, 0, 0, tickDelta);
        });
    }
}
