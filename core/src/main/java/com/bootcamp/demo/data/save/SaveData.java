package com.bootcamp.demo.data.save;

import com.bootcamp.demo.data.save.gears.GearSkinsSaveData;
import com.bootcamp.demo.data.save.gears.GearsSaveData;
import com.bootcamp.demo.data.save.pets.PetsSaveData;
import com.bootcamp.demo.data.save.tacticals.EquippedTacticalsSaveData;
import com.bootcamp.demo.data.save.tacticals.TacticalsSaveData;
import lombok.Getter;

public class SaveData {
    @Getter
    private final TacticalsSaveData tacticalsSaveSata;
    @Getter
    private final GearsSaveData gearsSaveData;
    @Getter
    private final GearSkinsSaveData gearSkins;
    @Getter
    private final PetsSaveData petsSaveData;
    @Getter
    private final InventorySaveData inventorySaveData;

    public SaveData() {
        tacticalsSaveSata = new TacticalsSaveData();
        gearsSaveData = new GearsSaveData();
        gearSkins = new GearSkinsSaveData();
        petsSaveData = new PetsSaveData();
        inventorySaveData = new InventorySaveData();
    }
}
