package com.bootcamp.demo.data.save;

import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;
import com.bootcamp.demo.data.save.tacticals.EquippedTacticalsSaveData;
import com.bootcamp.demo.data.save.tacticals.TacticalSaveData;
import com.bootcamp.demo.data.save.tacticals.TacticalsSaveData;
import com.bootcamp.demo.managers.API;
import lombok.Getter;

public class InventorySaveData implements Json.Serializable {
    @Getter
    EquippedTacticalsSaveData equippedTacticalsSaveData;
    public InventorySaveData() {
        equippedTacticalsSaveData = new EquippedTacticalsSaveData();
    }

    @Override
    public void write(Json json) {
        json.writeValue("equippedTacticals", equippedTacticalsSaveData);
    }

    @Override
    public void read(Json json, JsonValue jsonValue) {
        equippedTacticalsSaveData.read(json, jsonValue.get("equippedTacticals"));
    }
}
