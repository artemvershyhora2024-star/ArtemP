package com.artempvp;

import com.artempvp.hud.components.TopBarHud;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ArtemPvpClient implements ClientModInitializer {
    public static final String MOD_ID = "artempvp-client";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    // Создаем экземпляр нашей верхней панели
    public static TopBarHud topBarHud;

    @Override
    public void onInitializeClient() {
        LOGGER.info("ArtemPVP Client initialized!");

        // Инициализируем HUD вверху экрана (координаты X=10, Y=10)
        topBarHud = new TopBarHud(10, 10);

        // Регистрируем отрисовку через Fabric API, чтобы HUD показывался в игре
        HudRenderCallback.EVENT.register((drawContext, tickDelta) -> {
            // Передаем параметры рендеринга в нашу верхнюю панель
            topBarHud.render(drawContext, 0, 0, tickDelta);
        });
    }
}
