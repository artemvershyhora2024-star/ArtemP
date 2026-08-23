package com.artempvp.hud.components;

import com.artempvp.hud.DraggableHudComponent;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.item.ItemStack;

public class CooldownsHud extends DraggableHudComponent {

    public CooldownsHud(int x, int y) {
        super(x, y, 160, 100); // Немного увеличили высоту под несколько предметов
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client.player == null) return;

        // Темно-фиолетовый фон в стиле Syntax
        context.fill(x, y, x + width, y + height, 0xCC0B001A);

        // Заголовок панели
        context.drawText(client.textRenderer, "⏱ Cooldowns", x + 8, y + 8, 0xFFFFFFFF, true);

        int startY = y + 26;

        // Проверяем кулдауны для ключевых PvP-предметов
        // 1. Золотое яблоко (чарка)
        renderItemCooldown(context, client, Items.GOLDEN_APPLE, "Чарка", startY);
        
        // 2. Жемчуг Края (эдерка)
        renderItemCooldown(context, client, Items.ENDER_PEARL, "Эндер-жемчуг", startY + 20);

        // 3. Тотем бессмертия (если есть кулдаун)
        renderItemCooldown(context, client, Items.TOTEM_OF_UNDYING, "Тотем", startY + 40);
    }

    // Универсальный метод для отрисовки кулдауна конкретного предмета
    private void renderItemCooldown(DrawContext context, MinecraftClient client, Item item, String label, int posY) {
        if (client.player == null) return;

        // Получаем менеджер кулдаунов клиента/сервера
        float cooldownProgress = client.player.getItemCooldownManager().getCooldownProgress(item, client.getRenderTickCounter().getTickDelta());

        if (cooldownProgress > 0.0f) {
            // Если предмет на перезарядке — показываем секунды и шкалу
            double secondsLeft = Math.ceil(cooldownProgress * 5.0); // Примерный расчет времени
            context.drawText(client.textRenderer, "§c⚡ " + label, x + 8, posY, 0xFFFF5555, true);
            context.drawText(client.textRenderer, secondsLeft + "с", x + 125, posY, 0xFFAAAAAA, true);

            // Полоска прогресса кулдауна
            context.fill(x + 8, posY + 11, x + 152, posY + 14, 0xFF221133);
            context.fill(x + 8, posY + 11, x + 8 + (int)(144 * cooldownProgress), posY + 14, 0xFFBA55D3);
        } else {
            // Если предмет готов к использованию
            context.drawText(client.textRenderer, "§a✔ " + label, x + 8, posY, 0xFF55FF55, true);
            context.drawText(client.textRenderer, "Готов", x + 120, posY, 0xFF777777, true);
        }
    }
}
