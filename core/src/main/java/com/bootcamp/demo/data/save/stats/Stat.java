package com.bootcamp.demo.data.save.stats;

import lombok.Getter;

public enum Stat {
    HP("HP:", Type.ADDITIVE, false),
    ATTACK("ATK:", Type.ADDITIVE, false),
    DODGE("DODGE:", Type.MULTIPLICATIVE, true),
    COMBO("COMBO:", Type.MULTIPLICATIVE, true),
    CRITICAL("CRIT:", Type.MULTIPLICATIVE, true),
    STUN("STUN:", Type.MULTIPLICATIVE, true),
    REGENERATION("REGEN:", Type.ADDITIVE, false),
    STEAL("STEAL:", Type.MULTIPLICATIVE, true),
    POISON("POISON:", Type.MULTIPLICATIVE, true),
    ;

    @Getter
    private final String title;
    @Getter
    private final Type defaultType;
    @Getter
    private final boolean defaultRequired;

    Stat(String title, Type defaultType, boolean defaultRequired) {
        this.title = title;
        this.defaultType = defaultType;
        this.defaultRequired = defaultRequired;
    }

    public static Stat getStatByTitle(String value) {
        for (Stat stat : Stat.values()) {
            if(value.equals(stat.title)) return stat;
        }
        throw new IllegalArgumentException("Unknown stat title: " + value);
    }

    public enum Type {
        ADDITIVE,
        MULTIPLICATIVE
        ;
    }
}
