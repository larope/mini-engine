package com.bootcamp.demo.data.save;

import lombok.Getter;

import java.util.Objects;

public enum Stat {
    HP("HP:"),
    ATTACK("ATK:"),
    DODGE("DODGE:"),
    COMBO("COMBO:"),
    CRITICAL("CRIT:"),
    STUN("STUN:"),
    REGENERATION("REGEN:"),
    STEAL("STEAL:"),
    POISON("POISON:"),
    ;

    @Getter
    private final String title;

    Stat(String title){
        this.title = title;
    }

    public static Stat getStatByTitle(String value) {
        for (Stat stat : Stat.values()) {
            if(value.equals(stat.title)) return stat;
        }
        throw new IllegalArgumentException("Unknown stat title: " + value);
    }

}
