package com.bootcamp.demo.data.save;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.utils.ObjectMap;

import java.util.HashMap;
import java.util.Map;

public class StatsSaveData implements Json.Serializable {
    private final ObjectMap<Stat, Float> stats = new ObjectMap<Stat, Float>();

    public ObjectMap<Stat, Float> getStats() {
        if(!isFilled()){
            setDefaults();
        }
        return stats;
    }

    @Override
    public void write (Json json) {
        for (ObjectMap.Entry<Stat, Float> entry : stats.entries()) {
            json.writeValue(entry.key.getTitle(), entry.value);
        }
    }

    @Override
    public void read (Json json, JsonValue jsonValue) {
        stats.clear();

        for (JsonValue value : jsonValue) {
            Stat stat = Stat.getStatByTitle(value.name);
            float amount = value.asFloat();
            Gdx.app.log("LOG", value.name + " " + amount);

            stats.put(stat, amount);
        }
    }

    public boolean isFilled(){
        return stats.size == Stat.values().length;
    }

    public void setDefaults(){
        for (int i = 0; i < Stat.values().length; i++) {
            if(stats.containsKey(Stat.values()[i])) continue;

            stats.put(Stat.values()[i], 0f);
        }
    }
}
