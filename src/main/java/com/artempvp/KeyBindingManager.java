package com.artempvp;

import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

public class KeyBindingManager {
    public static KeyBinding openMenuKey;

    public static void registerKeys() {
        // Регистрируем клавишу RSHIFT для открытия меню клиента / настройки HUD
        openMenuKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.artempvp.menu",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_RIGHT_SHIFT, // Правый Shift
                "category.artempvp.general"
        ));
    }
}
