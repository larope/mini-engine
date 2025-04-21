package com.bootcamp.demo.data.save;

import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;
import com.bootcamp.demo.data.game.Gear;
import lombok.Getter;
import lombok.Setter;

public class GearSaveData implements Json.Serializable {
    @Getter @Setter
    private Gear name;
    @Getter @Setter
    private int level;;

    @Override
    public void write(Json json) {
        json.writeValue("name", name.getTitle());
        json.writeValue("level", level);
    }

    @Override
    public void read(Json json, JsonValue jsonValue) {
        name = Gear.fromName(jsonValue.getString("name"));
        level = jsonValue.getInt("level");
    }
}
