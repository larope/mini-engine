package com.bootcamp.demo.data.save;

import com.badlogic.gdx.utils.IntMap;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;
import com.bootcamp.demo.data.game.Gear;

public class GearsSaveData implements Json.Serializable {
    // TODO abstract this shit to higher level to inherit cause tacticals do the same
    private final int gearsCount = 6;
    private final IntMap<GearSaveData> gears = new IntMap<>();


    public IntMap<GearSaveData> getGears() {
        if (!isFilled()) {
            setDefaults();
        }
        return gears;
    }
    @Override
    public void write(Json json) {
        for (IntMap.Entry<GearSaveData> entry : gears.entries()) {
            json.writeValue(String.valueOf(entry.key), entry.value);
        }
    }

    @Override
    public void read(Json json, JsonValue jsonValue) {
        gears.clear();

        for (JsonValue value : jsonValue) {
            final Integer slotIndex = Integer.valueOf(value.name);
            final GearSaveData data = json.readValue(GearSaveData.class, value);
            gears.put(slotIndex, data);
        }
    }

    public boolean isFilled(){
        return gears.size == gearsCount;
    }

    public void setDefaults(){
        for (int i = 0; i < gearsCount; i++) {
            if(gears.containsKey(i)) continue;
            GearSaveData defaultGear = new GearSaveData();
            defaultGear.setLevel(0);
            defaultGear.setName(Gear.EMPTY);

            gears.put(i, defaultGear);
        }
    }
}
