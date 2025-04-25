package com.bootcamp.demo.data.save.tacticals;

import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.utils.ObjectMap;
import lombok.Getter;

public class TacticalsSaveData implements Json.Serializable {
    @Getter
    private final ObjectMap<String, TacticalSaveData> tacticals = new ObjectMap<>();

    public void addTactical(TacticalSaveData tactical) {
        tacticals.put(tactical.getName(), tactical);
    }

    @Override
    public void write (Json json) {
        for (ObjectMap.Entry<String, TacticalSaveData> entry : tacticals) {
            json.writeValue(entry.key, entry.value);
        }
    }

    @Override
    public void read (Json json, JsonValue jsonValue) {
        tacticals.clear();

        for (JsonValue value : jsonValue) {
            final TacticalSaveData data = json.readValue(TacticalSaveData.class, value);
            tacticals.put(data.getName(), data);
        }
    }
}
