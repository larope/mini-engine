package com.bootcamp.demo.data.save;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.IntMap;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.utils.Logger;
import com.bootcamp.demo.managers.API;
import lombok.Getter;

import java.util.Dictionary;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

public class StatsSaveData implements Json.Serializable {
    @Getter
    private final Map<Stat, Float> stats = new HashMap<Stat, Float>();

    @Override
    public void write (Json json) {
        for (Map.Entry<Stat, Float> entry : stats.entrySet()) {
            json.writeValue(entry.getKey().getTitle(), entry.getValue());
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
        return stats.size() == Stat.values().length;
    }

    public void setDefaults(){
        for (int i = 0; i < Stat.values().length; i++) {
            if(Stat.values()[i] == Stat.NOTFOUND) continue;
            stats.put(Stat.values()[i], 0f);
        }
    }
}
