package com.bootcamp.demo.data.save;

import com.badlogic.gdx.utils.IntMap;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.utils.ObjectMap;
import com.bootcamp.demo.data.game.GearType;

public class GearsSaveData implements Json.Serializable {
    private final static int gearsCount = 6;
    private final ObjectMap<GearType, GearSaveData> gears = new ObjectMap<>();


    public ObjectMap<GearType, GearSaveData> getGears() {
        if (!isFilled()) {
            setDefaults();
        }

        return gears;
    }
    @Override
    public void write(Json json) {
        for (ObjectMap.Entry<GearType, GearSaveData> entry : gears) {
            json.writeValue(String.valueOf(entry.key), entry.value);
        }
    }

    @Override
    public void read(Json json, JsonValue jsonValue) {
        gears.clear();

        for (JsonValue value : jsonValue) {
            final GearSaveData data = json.readValue(GearSaveData.class, value);
            gears.put(data.getType(), data);
        }
    }

    public boolean isFilled(){
        return gears.size == gearsCount;
    }

    public void setDefaults(){
        for (GearType type : GearType.values()) {
            if(gears.containsKey(type)) {
                continue;
            }

            GearSaveData defaultGear = new GearSaveData();
            defaultGear.setLevel(0);
            defaultGear.setType(type);
            defaultGear.setSkin("BootsOfPhallus");
            defaultGear.setDefaults();

            defaultGear.getStats().setRandoms();

            gears.put(type, defaultGear);
        }
    }
}
