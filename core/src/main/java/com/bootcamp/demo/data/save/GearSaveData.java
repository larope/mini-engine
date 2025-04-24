package com.bootcamp.demo.data.save;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.utils.ObjectMap;
import com.bootcamp.demo.data.game.GearType;
import lombok.Getter;
import lombok.Setter;

import java.util.Locale;

public class GearSaveData implements Json.Serializable {
    @Getter @Setter
    private GearType type;
    @Getter @Setter
    private int level;
    @Getter @Setter
    private int starCount;
    @Getter @Setter
    private String skin;

    private Stats stats = new Stats();

    public Stats getStats(){
        if(!stats.isFilled()){
            stats.setDefaults();
        }

        return stats;
    }

    @Override
    public void write(Json json) {
        json.writeValue("t", type.name());
        json.writeValue("l", level);
        json.writeValue("s", starCount);
        json.writeValue("sk", skin);
        json.writeValue("st", stats);
    }

    @Override
    public void read(Json json, JsonValue jsonValue) {
        type = GearType.valueOf(jsonValue.getString("t").toUpperCase(Locale.ENGLISH));
        level = jsonValue.getInt("l");
        starCount = jsonValue.getInt("s");
        skin = jsonValue.getString("sk");
        stats = json.readValue(Stats.class, jsonValue.get("st"));
    }

    public void setDefaults(){
        stats.setDefaults();
    }

    @Override
    public String toString () {
        return type.name() + " " + level + " " + starCount + " " + skin + " " + stats.toString();
    }
}
