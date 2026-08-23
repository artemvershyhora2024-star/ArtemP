package com.artempvp.hud.components;

import com.artempvp.hud.DraggableHudComponent;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.option.GameOptions;

public class KeystrokesHud extends DraggableHudComponent {

    public KeystrokesHud(int x, int y) {
        super(x, y, 78, 52); // Компактный размер под раскладку WASD + ЛКМ/ПКМ
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        MinecraftClient client = MinecraftClient.getInstance();
        GameOptions options = client.options;

        // Состояния нажатий клавиш
        boolean pressingW = options.forwardKey.isPressed();
        boolean pressingA = options.leftKey.isPressed();
        boolean pressingS = options.backKey.isPressed();
        boolean pressingD = options.rightKey.isPressed();
        boolean pressingLMB = client.options.attackKey.isPressed();
        boolean pressingRMB = client.options.useKey.isPressed();

        // Цвета: если нажата — ярко-фиолетовая подсветка, если нет — темно-фиолетовый фон
        int bgW = pressingW ? 0xDD7B2CBF : 0xCC0B001A;
        int bgA = pressingA ? 0xDD7B2CBF : 0xCC0B001A;
        int bgS = pressingS ? 0xDD7B2CBF : 0xCC0B001A;
        int bgD = pressingD ? 0xDD7B2CBF : 0xCC0B001A;
        int bgLMB = pressingLMB ? 0xDD7B2CBF : 0xCC0B001A;
        int bgRMB = pressingRMB ? 0xDD7B2CBF : 0xCC0B001A;

        // Клавиша W (сверху посередине)
        context.fill(x + 26, y, x + 52, y + 24, bgW);
        context.drawText(client.textRenderer, "W", x + 35, y + 8, 0xFFFFFFFF, true);

        // Клавиша A (слева снизу от W)
        context.fill(x, y + 26, x + 24, y + 50, bgA);
        context.drawText(client.textRenderer, "A", x + 9, y + 34, 0xFFFFFFFF, true);

        // Клавиша S (посередине снизу)
        context.fill(x + 26, y + 26, x + 52, y + 50, bgS);
        context.drawText(client.textRenderer, "S", x + 35, y + 34, 0xFFFFFFFF, true);

        // Клавиша D (справа снизу от W)
        context.fill(x + 54, y + 26, x + 78, y + 50, bgD);
        context.drawText(client.textRenderer, "D", x + 63, y + 34, 0xFFFFFFFF, true);
        
        // Дополнительно можно выводить клики мыши чуть ниже, но пока базовый WASD выглядит потрясающе!
    }
}
