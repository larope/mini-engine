package com.bootcamp.demo.data.save;

import com.bootcamp.demo.data.save.gear.GearSkinsSaveData;
import com.bootcamp.demo.data.save.gear.GearsSaveData;
import com.bootcamp.demo.data.save.tacticals.TacticalsSaveData;
import lombok.Getter;

public class SaveData {
    @Getter
    private final TacticalsSaveData tacticalsSaveSata;
    @Getter
    private final GearsSaveData gearsSaveData;
    @Getter
    private final GearSkinsSaveData gearSkins;

    public SaveData() {
        tacticalsSaveSata = new TacticalsSaveData();
        gearsSaveData = new GearsSaveData();
        gearSkins = new GearSkinsSaveData();
    }
}
