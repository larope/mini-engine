package com.bootcamp.demo.data.save;

import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;
import lombok.Getter;
import lombok.Setter;

import java.util.Locale;

public class TacticalSaveData implements Json.Serializable {

    @Getter @Setter
    private String name;
    @Getter @Setter
    private int level;
    @Getter @Setter
    private int count;

    private Stats stats = new Stats();

    public Stats getStats(){
        if(!stats.isFilled()){
            stats.setRandoms();
        }
        return stats;
    }


    @Override
    public void write (Json json) {
        json.writeValue("n", name);
        json.writeValue("l", level);
        json.writeValue("st", stats);
        json.writeValue("c", count);
    }

    @Override
    public void read (Json json, JsonValue jsonValue) {
        name = jsonValue.getString("n");
        level = jsonValue.getInt("l");
        stats = json.readValue(Stats.class, jsonValue.get("st"));
        count = jsonValue.getInt("c");
    }
}
