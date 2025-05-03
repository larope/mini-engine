package com.bootcamp.demo.data.save.tacticals;

import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;
import com.bootcamp.demo.data.game.Rarity;
import com.bootcamp.demo.data.save.stats.Stats;
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
    @Getter @Setter
    private Rarity rarity;

    @Setter
    private Stats stats = new Stats();

    public Stats getStats(){
        if(!stats.isFilled()){
            stats.setDefaults();
        }
        return stats;
    }

    @Override
    public void write (Json json) {
        json.writeValue("n", name);
        json.writeValue("l", level);
        json.writeValue("c", count);
        json.writeValue("r", rarity);
        json.writeValue("st", stats);
    }

    @Override
    public void read (Json json, JsonValue jsonValue) {
        name = jsonValue.getString("n");
        level = jsonValue.getInt("l");
        count = jsonValue.getInt("c");
        rarity = Rarity.valueOf(jsonValue.getString("r").toUpperCase(Locale.ENGLISH));
        stats = json.readValue(Stats.class, jsonValue.get("st"));
    }
}
