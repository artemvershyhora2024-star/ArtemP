package com.artempvp.hud.components;

import com.artempvp.hud.DraggableHudComponent;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;

public class InventoryHud extends DraggableHudComponent {

    public InventoryHud(int x, int y) {
        super(x, y, 180, 70); // Размер под сетку предметов
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client.player == null) return;

        // Темно-фиолетовый фон панели инвентаря
        context.fill(x, y, x + width, y + height, 0xCC0B001A);

        // Заголовок
        context.drawText(client.textRenderer, "📦 Inventory", x + 8, y + 8, 0xFFFFFFFF, true);

        // Отрисовка реальных предметов из инвентаря игрока (первые 9 слотов главного инвентаря)
        int startX = x + 8;
        int startY = y + 24;

        for (int i = 0; i < 9; i++) {
            net.minecraft.item.ItemStack stack = client.player.getInventory().getStack(i + 9); // Строка инвентаря над хотбаром
            int slotX = startX + (i * 18);
            
            // Рисуем фон под каждый слот
            context.fill(slotX, startY, slotX + 16, startY + 16, 0xFF221133);
            
            // Если в слоте есть предмет — рендерим его и количество
            if (!stack.isEmpty()) {
                context.drawItem(stack, slotX, startY);
                context.drawItemInSlot(client.textRenderer, stack, slotX, startY);
            }
        }
    }
}
