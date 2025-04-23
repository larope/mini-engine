package com.bootcamp.demo.data.save;

import com.badlogic.gdx.utils.IntMap;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;

public class TacticalsSaveData implements Json.Serializable {
    // TODO abstract this shit to higher level to inherit cause gears do the same
    private final int tacticalsCount = 4;
    private final IntMap<TacticalSaveData> tacticals = new IntMap<>();


    public IntMap<TacticalSaveData> getTacticals() {
        if (!isFilled()) {
            setDefaults();
        }
        return tacticals;
    }

    @Override
    public void write (Json json) {
        for (IntMap.Entry<TacticalSaveData> entry : tacticals.entries()) {
            json.writeValue(String.valueOf(entry.key), entry.value);
        }
    }

    @Override
    public void read (Json json, JsonValue jsonValue) {
        tacticals.clear();

        for (JsonValue value : jsonValue) {
            final Integer slotIndex = Integer.valueOf(value.name);
            final TacticalSaveData data = json.readValue(TacticalSaveData.class, value);
            tacticals.put(slotIndex, data);
        }
    }

    public boolean isFilled(){
        return tacticals.size == tacticalsCount;
    }

    public void setDefaults(){
        for (int i = 0; i < tacticalsCount; i++) {
            if(tacticals.containsKey(i)) continue;
            TacticalSaveData defaultTactical = new TacticalSaveData();
            defaultTactical.setLevel(0);


            tacticals.put(i, defaultTactical);
        }
    }
}
