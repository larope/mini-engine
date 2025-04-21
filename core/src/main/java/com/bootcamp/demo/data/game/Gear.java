package com.bootcamp.demo.data.game;

import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.bootcamp.demo.engine.Resources;
import lombok.Getter;

public enum Gear implements IDataType {
    BOOTS("boots", "shovel-icon"),
    GLOVES("gloves", "shovel-icon"),
    HAT("hat", "shovel-icon"),
    AXE("axe", "shovel-icon"),
    GUN("gun", "shovel-icon"),
    SHIRT("shirt", "shovel-icon"),
    EMPTY("empty", "tier-coin-icon"),
    ;
    @Getter
    private final String title;
    @Getter
    private final String drawablePath;

    Gear(String title, String drawablePath) {
        this.title = title;
        this.drawablePath = drawablePath;
    }

    public Drawable getDrawable() {
        return Resources.getDrawable("ui/" + drawablePath);
    }

    public static Gear fromName(String name) {
        for (Gear type : values()) {
            if (type.title.equals(name)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Unknown gear name: " + name);
    }
}
