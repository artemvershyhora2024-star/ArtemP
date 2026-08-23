package com.artempvp.module;

import com.artempvp.ArtemPvpClient;
import com.artempvp.ArtemPvpUtilities;
import com.artempvp.ArtemPvpPvPUtilities;
import com.artempvp.ArtemPvpVisualAndPvP2;

import java.util.ArrayList;
import java.util.List;

public class ModuleManager {
    private static final List<Module> modules = new ArrayList<>();

    public static void init() {
        // --- ВИЗУАЛЬНЫЕ МОДУЛИ ---
        modules.add(new Module("Ambience", Module.Category.VISUALS, false));
        modules.add(new Module("Aspect Ratio", Module.Category.VISUALS, false));
        modules.add(new Module("China Hat", Module.Category.VISUALS, false));
        modules.add(new Module("Block Overlay", Module.Category.VISUALS, true) {
            @Override public void onToggle() { ArtemPvpPvPUtilities.blockOverlayEnabled = isEnabled(); }
        });
        modules.add(new Module("Item Physic", Module.Category.VISUALS, true) {
            @Override public void onToggle() { ArtemPvpVisualAndPvP2.itemPhysicEnabled = isEnabled(); }
        });
        modules.add(new Module("Motion Blur", Module.Category.VISUALS, true) {
            @Override public void onToggle() { ArtemPvpVisualAndPvP2.motionBlurEnabled = isEnabled(); }
        });
        modules.add(new Module("No Render", Module.Category.VISUALS, false));
        modules.add(new Module("Target ESP", Module.Category.VISUALS, false));
        modules.add(new Module("Optimization", Module.Category.VISUALS, true));
        modules.add(new Module("Fullbright", Module.Category.VISUALS, false) {
            @Override public void onToggle() { ArtemPvpUtilities.toggleFullbright(); }
        });

        // --- УТИЛИТЫ ПВП ---
        modules.add(new Module("Auto Respawn", Module.Category.UTILITIES, true) {
            @Override public void onToggle() { ArtemPvpPvPUtilities.autoRespawnEnabled = isEnabled(); }
        });
        modules.add(new Module("Death Coordinates", Module.Category.UTILITIES, true) {
            @Override public void onToggle() { ArtemPvpPvPUtilities.deathCoordinatesEnabled = isEnabled(); }
        });
        modules.add(new Module("Fast EXP", Module.Category.UTILITIES, false));
        modules.add(new Module("Healing Helper", Module.Category.UTILITIES, false));
        modules.add(new Module("Item Scrolling", Module.Category.UTILITIES, false));
        modules.add(new Module("Totem Tracker", Module.Category.UTILITIES, true) {
            @Override public void onToggle() { ArtemPvpVisualAndPvP2.totemTrackerEnabled = isEnabled(); }
        });
        modules.add(new Module("Auto Sprint", Module.Category.UTILITIES, true) {
            @Override public void onToggle() { ArtemPvpUtilities.autoSprintEnabled = isEnabled(); }
        });

        // --- HUD МОДУЛИ ---
        modules.add(new Module("Music Player HUD", Module.Category.HUD, true) {
            @Override public void onToggle() { ArtemPvpMenuScreen.musicHudEnabled = isEnabled(); }
        });
        modules.add(new Module("Cooldowns HUD", Module.Category.HUD, true) {
            @Override public void onToggle() { ArtemPvpMenuScreen.cooldownsHudEnabled = isEnabled(); }
        });
    }

    public static List<Module> getModules() {
        return modules;
    }

    public static List<Module> getModulesByCategory(Module.Category category) {
        List<Module> result = new ArrayList<>();
        for (Module m : modules) {
            if (m.getCategory() == category) {
                result.add(m);
            }
        }
        return result;
    }
}
