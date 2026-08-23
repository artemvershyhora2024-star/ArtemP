package com.artempvp;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.fabricmc.loader.api.FabricLoader;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class ArtemPvpConfig {
    private static final File CONFIG_FILE = FabricLoader.getInstance().getConfigDir().resolve("artempvp-config.json").toFile();
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    public static Data DATA = new Data();

    public static class Data {
        // Состояния кнопок меню
        public boolean musicHudEnabled = true;
        public boolean cooldownsHudEnabled = true;

        // Координаты всех HUD компонентов
        public int topBarX = 10, topBarY = 10;
        public int cooldownsX = 10, cooldownsY = 40;
        public int keybindsX = 10, keybindsY = 150;
        public int potionsX = 180, potionsY = 10;
        public int musicPlayerX = 330, musicPlayerY = 10;
        public int inventoryX = 330, inventoryY = 95;
        public int targetX = 330, targetY = 175;
        public int keystrokesX = 10, keystrokesY = 235;
    }

    public static void save() {
        // Записываем текущие координаты в объект данных перед сохранением
        if (ArtemPvpClient.topBarHud != null) {
            DATA.topBarX = ArtemPvpClient.topBarHud.getX();
            DATA.topBarY = ArtemPvpClient.topBarHud.getY();
            DATA.cooldownsX = ArtemPvpClient.cooldownsHud.getX();
            DATA.cooldownsY = ArtemPvpClient.cooldownsHud.getY();
            DATA.keybindsX = ArtemPvpClient.keybindsHud.getX();
            DATA.keybindsY = ArtemPvpClient.keybindsHud.getY();
            DATA.potionsX = ArtemPvpClient.potionsHud.getX();
            DATA.potionsY = ArtemPvpClient.potionsHud.getY();
            DATA.musicPlayerX = ArtemPvpClient.musicPlayerHud.getX();
            DATA.musicPlayerY = ArtemPvpClient.musicPlayerHud.getY();
            DATA.inventoryX = ArtemPvpClient.inventoryHud.getX();
            DATA.inventoryY = ArtemPvpClient.inventoryHud.getY();
            DATA.targetX = ArtemPvpClient.targetHud.getX();
            DATA.targetY = ArtemPvpClient.targetHud.getY();
            DATA.keystrokesX = ArtemPvpClient.keystrokesHud.getX();
            DATA.keystrokesY = ArtemPvpClient.keystrokesHud.getY();
        }

        try (FileWriter writer = new FileWriter(CONFIG_FILE)) {
            GSON.toJson(DATA, writer);
        } catch (IOException e) {
            ArtemPvpClient.LOGGER.error("Failed to save ArtemPVP config!", e);
        }
    }

    public static void load() {
        if (CONFIG_FILE.exists()) {
            try (FileReader reader = new FileReader(CONFIG_FILE)) {
                DATA = GSON.fromJson(reader, Data.class);
            } catch (IOException e) {
                ArtemPvpClient.LOGGER.error("Failed to load ArtemPVP config!", e);
            }
        } else {
            save();
        }
    }
}
