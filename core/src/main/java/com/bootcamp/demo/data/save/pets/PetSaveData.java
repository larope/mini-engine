package com.bootcamp.demo.data.save.pets;

import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;
import com.bootcamp.demo.data.save.stats.Stats;
import lombok.Getter;
import lombok.Setter;

public class PetSaveData implements Json.Serializable {
    @Getter @Setter
    private String name;

    @Setter
    private Stats stats = new Stats();

    public Stats getStats(){
        if(!stats.isFilled()){
            stats.setDefaults();
        }

        return stats;
    }
    @Override
    public void write(Json json) {
        json.writeValue("n", name);
        json.writeValue("st", stats);
    }

    @Override
    public void read(Json json, JsonValue jsonValue) {
        name = jsonValue.getString("n");
        stats = json.readValue(Stats.class, jsonValue.get("st"));
    }
}
