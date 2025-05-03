package com.bootcamp.demo.data.game;

import com.badlogic.gdx.graphics.Color;
import lombok.Getter;

public enum Rarity {
    RUSTED(Color.valueOf("#7E6652"), Color.valueOf("#5E4F3E")),
    SCRAP(Color.valueOf("#8A8A8A"), Color.valueOf("#6A6A6A")),
    HARDENED(Color.valueOf("#548C5C"), Color.valueOf("#3F6845")),
    ELITE(Color.valueOf("#4A7BB5"), Color.valueOf("#355C86")),
    ASCENDANT(Color.valueOf("#985CD1"), Color.valueOf("#7547A1")),
    NUCLEAR(Color.valueOf("#D1C54A"), Color.valueOf("#A49A36")),
    JUGGERNAUT(Color.valueOf("#D17339"), Color.valueOf("#9E552B")),
    DOMINION(Color.valueOf("#B03A48"), Color.valueOf("#852B35")),
    OBLIVION(Color.valueOf("#2E2D4D"), Color.valueOf("#232236")),
    IMMORTAL(Color.valueOf("#00FFFF"), Color.valueOf("#00BFBF")),
    ;

    @Getter
    private final Color backgroundColor;
    @Getter
    private final Color borderColor;

    Rarity(Color backgroundColor, Color borderColor) {
        this.backgroundColor = backgroundColor;
        this.borderColor = borderColor;
    }

    public static Rarity getRandomRarity() {
        Rarity[] values = values();
        return values[(int) (Math.random() * values.length)];
    }
}
