package com.artempvp.module;

public class Module {
    private final String name;
    private final Category category;
    private boolean enabled;

    public Module(String name, Category category, boolean defaultState) {
        this.name = name;
        this.category = category;
        this.enabled = defaultState;
    }

    public String getName() {
        return name;
    }

    public Category getCategory() {
        return category;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
        onToggle();
    }

    public void toggle() {
        setEnabled(!enabled);
    }

    public void onToggle() {}

    public enum Category {
        VISUALS("Визуальные"),
        UTILITIES("Утилиты PvP"),
        HUD("Элементы HUD");

        private final String displayName;
        Category(String displayName) {
            this.displayName = displayName;
        }
        public String getDisplayName() {
            return displayName;
        }
    }
}
