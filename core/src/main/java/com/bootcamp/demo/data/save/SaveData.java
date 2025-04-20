package com.bootcamp.demo.data.save;

import com.bootcamp.demo.managers.API;
import lombok.Getter;

import java.util.Random;

public class SaveData {

    @Getter
    private final TacticalsSaveData tacticalsSaveData = new TacticalsSaveData();

    @Getter
    private final StatsSaveData statsSaveData = new StatsSaveData();

    public SaveData() {
        if(!statsSaveData.isFilled()) {
            statsSaveData.setDefaults();
        }
    }
}
