package com.bootcamp.demo.data.save.gears;

import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.utils.ObjectMap;
import com.bootcamp.demo.data.game.GameData;
import com.bootcamp.demo.data.game.gear.GearGameData;
import com.bootcamp.demo.data.game.gear.GearType;
import com.bootcamp.demo.data.game.gear.GearsGameData;
import com.bootcamp.demo.data.save.stats.Stats;
import com.bootcamp.demo.managers.API;
import lombok.Getter;
import lombok.Setter;

import java.util.Locale;

public class GearSkinSaveData implements Json.Serializable {
    @Getter @Setter
    String name;
    @Getter @Setter
    GearType type;
    @Setter
    Stats stats;

    public Stats getStats(){
        if(!stats.isFilled()){
            stats.setDefaults();
        }

        return stats;
    }

    @Override
    public void write(Json json) {
        json.writeValue("n", name);
        json.writeValue("t", type);
        json.writeValue("s", stats);
    }

    @Override
    public void read(Json json, JsonValue jsonValue) {
        name = jsonValue.getString("n");
        type = GearType.valueOf(jsonValue.getString("t").toUpperCase(Locale.ENGLISH));

        stats = json.readValue(Stats.class, jsonValue.get("s"));
    }

    public Drawable getDrawable(){
        final GearsGameData gameData = API.get(GameData.class).getGearsGameData();
        ObjectMap<String, GearGameData> gearSkins = gameData.getGears().get(type);
        return gearSkins.get(name).getDrawable();
    }
}
