package com.artempvp.hud.components;

import com.artempvp.hud.DraggableHudComponent;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;

public class TargetHud extends DraggableHudComponent {
    private LivingEntity targetEntity = null;

    public TargetHud(int x, int y) {
        super(x, y, 170, 55); // Размер окошка таргета
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client.player == null) return;

        // Проверяем, на кого смотрит игрок в данный момент
        HitResult hit = client.crosshairTarget;
        if (hit != null && hit.getType() == HitResult.Type.ENTITY) {
            Entity entity = ((EntityHitResult) hit).getEntity();
            if (entity instanceof LivingEntity) {
                targetEntity = (LivingEntity) entity;
            }
        }

        // Если цель не найдена или слишком далеко — не рендерим или показываем заглушку
        if (targetEntity == null || targetEntity.isDead()) {
            // Опционально: можно не рисовать, если никто не выбран, либо показывать заглушку для красоты
            return;
        }

        // Темно-фиолетовый фон в стиле Syntax
        context.fill(x, y, x + width, y + height, 0xCC0B001A);

        // Имитация аватара головы игрока/моба слева
        context.fill(x + 8, y + 8, x + 40, y + 40, 0xFF2A1B3D);

        // Ник цели
        String targetName = targetEntity.getName().getString();
        if (targetName.length() > 14) targetName = targetName.substring(0, 14) + "...";
        context.drawText(client.textRenderer, "§d" + targetName, x + 48, y + 8, 0xFFFFFFFF, true);

        // Расстояние до цели
        double distance = client.player.distanceTo(targetEntity);
        String distText = String.format("§7Дистанция: §f%.1fm", distance);
        context.drawText(client.textRenderer, distText, x + 48, y + 20, 0xFFAAAAAA, true);

        // Шкала здоровья цели
        float health = targetEntity.getHealth();
        float maxHealth = targetEntity.getMaxHealth();
        float healthPercent = Math.max(0f, Math.min(1f, health / maxHealth));

        // Полоска здоровья
        context.fill(x + 48, y + 36, x + 162, y + 44, 0xFF221133); // Фон шкалы
        context.fill(x + 48, y + 36, x + 48 + (int)(114 * healthPercent), y + 44, 0xFFFF5555); // Заполнение здоровья

        // Текст здоровья
        String hpText = String.format("%.1f/%.1f❤", health, maxHealth);
        context.drawText(client.textRenderer, hpText, x + 48, y + 45, 0xFFFFAAAA, false);
    }
}
