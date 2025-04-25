package com.bootcamp.demo.data.save.inventory;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.utils.ObjectMap;
import com.bootcamp.demo.data.game.gear.GearType;
import com.bootcamp.demo.data.save.gear.GearSkinSaveData;
import com.bootcamp.demo.data.save.gear.GearSkinsSaveData;
import lombok.Getter;

public class InventorySaveData implements Json.Serializable {
    @Getter
    private GearSkinsSaveData gearSkins;

    @Override
    public void write(Json json) {
        json.writeValue("gs", gearSkins);
    }

    @Override
    public void read(Json json, JsonValue jsonValue) {
        gearSkins = json.readValue(GearSkinsSaveData.class, jsonValue.get("gs"));
    }
}
