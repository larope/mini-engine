package com.bootcamp.demo.data.save;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectMap;
import com.bootcamp.demo.data.game.GearType;
import com.bootcamp.demo.util.NumberFormatter;
import lombok.Getter;

public class SaveData {
    @Getter
    private final TacticalsSaveData tacticalsSaveData = new TacticalsSaveData();
    @Getter
    private final GearsSaveData gearsSaveData = new GearsSaveData();

    private final ObjectMap<Stat, Float> stats = new ObjectMap<>();

    public SaveData() {
        tacticalsSaveData.setDefaults();
        gearsSaveData.setDefaults();


    }


}
