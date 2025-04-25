package com.bootcamp.demo.data.save;

import com.badlogic.gdx.utils.ObjectMap;
import com.bootcamp.demo.data.save.stats.Stat;
import com.bootcamp.demo.data.save.gear.GearsSaveData;
import com.bootcamp.demo.data.save.tacticals.TacticalsSaveData;
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
