package com.bootcamp.demo.data.save;

import lombok.Getter;

public class SaveData {
    @Getter
    private final TacticalsSaveData tacticalsSaveData = new TacticalsSaveData();
    @Getter
    private final GearsSaveData gearsSaveData = new GearsSaveData();
    @Getter
    private final StatsSaveData statsSaveData = new StatsSaveData();
}
