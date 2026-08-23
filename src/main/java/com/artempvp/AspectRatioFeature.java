package com.artempvp;

import com.artempvp.module.ModuleManager;

public class AspectRatioFeature {
    
    // Метод для получения коэффициента ширины экрана (множитель пропорций)
    public static float getAspectRatioMultiplier() {
        boolean enabled = ModuleManager.getModules().stream()
                .anyMatch(m -> m.getName().equals("Aspect Ratio") && m.isEnabled());
        
        if (enabled) {
            // Коэффициент 4:3 (растягивает картинку мира по горизонтали)
            return 1.333f; 
        }
        return 1.0f; // Стандартное соотношение 16:9
    }
}
