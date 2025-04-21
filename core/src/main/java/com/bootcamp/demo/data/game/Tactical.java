package com.bootcamp.demo.data.game;

import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.bootcamp.demo.engine.Resources;
import lombok.Getter;

public enum Tactical implements IDataType{
    SHURIKEN("shuriken", "pet-voucher"),
    NIGHT_VISION_GLASSES("night-vision-glasses", "pet-voucher"),
    COMPASS("compass", "pet-voucher"),
    DYNAMITE("dynamite", "pet-voucher"),
    EMPTY("empty", "mission-chest-red"),
    ;

    @Getter
    private final String title;
    @Getter
    private final String drawablePath;

    Tactical(String title, String drawablePath) {
        this.title = title;
        this.drawablePath = drawablePath;
    }

    public Drawable getDrawable() {
        return Resources.getDrawable("ui/" + drawablePath);
    }

    public static Tactical fromName(String name) {
        for (Tactical type : values()) {
            if (type.title.equals(name)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Unknown tactical name: " + name);
    }
}
