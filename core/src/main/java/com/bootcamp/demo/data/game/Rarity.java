package com.bootcamp.demo.data.game;

import com.badlogic.gdx.graphics.Color;
import lombok.Getter;

public enum Rarity {
    Rusted(Color.valueOf("#7e6652")),
    Scrap(Color.valueOf("#8a8a8a")),
    Hardened(Color.valueOf("#548c5c")),
    Elite(Color.valueOf("#4a7bb5")),
    Ascendant(Color.valueOf("#985cd1")),
    Nuclear(Color.valueOf("#d1c54a")),
    Juggernaut(Color.valueOf("#d17339")),
    Dominion(Color.valueOf("#b03a48")),
    Oblivion(Color.valueOf("#2e2d4d")),
    Immortal(Color.valueOf("#00ffff")),
    Ethereal(Color.valueOf("#f0e6ff"))
    ;

    @Getter
    private Color backgroundColor;

    Rarity(Color backgroundColor) {
        this.backgroundColor = backgroundColor;
    }
}
