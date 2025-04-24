package com.bootcamp.demo.data.save;

import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;
import lombok.Getter;
import lombok.Setter;

public class StatEntry implements Json.Serializable {
    @Getter @Setter
    Stat stat;
    @Getter @Setter
    Stat.Type statType;
    @Getter @Setter
    float value;

    @Override
    public void write(Json json) {
        json.writeValue("s", stat.name());
        json.writeValue("t", statType.name());
        json.writeValue("v", value);
    }

    @Override
    public void read(Json json, JsonValue jsonValue) {
        stat = Stat.valueOf(jsonValue.getString("s"));
        statType = Stat.Type.valueOf(jsonValue.getString("t"));
        value = jsonValue.getFloat("v");
    }
}
