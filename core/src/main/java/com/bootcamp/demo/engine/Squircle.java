package com.bootcamp.demo.engine;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import lombok.Getter;

public enum Squircle {
    SQUIRCLE_15(15, "white-squircle"),
    SQUIRCLE_35(35, "white-squircle"),
    SQUIRCLE_35_BORDER(35, "white-squircle-border"),
    SQUIRCLE_35_TOP(35, "white-squircle-top")
    ;

    @Getter
    private final int radius;
    private final String name;

    Squircle (int radius, String name) {
        this.radius = radius;
        this.name = name;
    }

    public String getRegionName () {
        return "basics/" + name + "-" + radius;
    }

    public Drawable getDrawable () {
        return getDrawable(Color.WHITE);
    }

    public Drawable getDrawable (Color color) {
        return Resources.getDrawable("basics/" + name + "-" + radius, color);
    }
}
