package com.bootcamp.demo.data.save;

import lombok.Getter;

public enum Stat {
    HP("HP:", StatType.ADDITIVE),
    ATTACK("ATK:", StatType.ADDITIVE),
    DODGE("DODGE:", StatType.MULTIPLICATIVE),
    COMBO("COMBO:", StatType.MULTIPLICATIVE),
    CRITICAL("CRIT:", StatType.MULTIPLICATIVE),
    STUN("STUN:", StatType.MULTIPLICATIVE),
    REGENERATION("REGEN:", StatType.ADDITIVE),
    STEAL("STEAL:", StatType.MULTIPLICATIVE),
    POISON("POISON:", StatType.MULTIPLICATIVE),
    ;

    @Getter
    private final String title;

    @Getter
    private final StatType defaultType;

    Stat(String title, StatType defaultType) {
        this.title = title;
        this.defaultType = defaultType;
    }

    public static Stat getStatByTitle(String value) {
        for (Stat stat : Stat.values()) {
            if(value.equals(stat.title)) return stat;
        }
        throw new IllegalArgumentException("Unknown stat title: " + value);
    }
    public enum StatType{
        ADDITIVE,
        MULTIPLICATIVE
        ;
    }
}
