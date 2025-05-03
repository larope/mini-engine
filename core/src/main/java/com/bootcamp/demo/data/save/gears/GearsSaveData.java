package com.bootcamp.demo.data.save.gears;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.utils.ObjectMap;
import com.bootcamp.demo.data.game.gear.GearType;
import com.bootcamp.demo.managers.RandomData;
import com.bootcamp.demo.managers.API;

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

            GearSaveData defaultGear = API.get(RandomData.class).getRandomGear(type, 0, 20, 1, 4);
            defaultGear.setStats(API.get(RandomData.class).getRandomStats(new Vector2(200, 500), new Vector2(0, 1.5f)));
            gears.put(type, defaultGear);
        }
    }
}
