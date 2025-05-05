package com.bootcamp.demo.data.save.gears;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;
import com.bootcamp.demo.data.game.Rarity;
import com.bootcamp.demo.data.game.gear.GearType;
import com.bootcamp.demo.data.save.SaveData;
import com.bootcamp.demo.data.save.stats.Stats;
import com.bootcamp.demo.managers.API;
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
    @Getter @Setter
    private Rarity rarity;

    @Setter
    private Stats stats = new Stats();

    public void setSkin(GearSkinSaveData skin) {
        assert (skin.type == type) : "The expected type for skin is " + type.name() + " but received " + skin.type.name();
        this.skin = skin.name;
    }

    public GearSkinSaveData getSkinData(){
        return API.get(SaveData.class).getGearSkins().getSkin(type, skin);
    }

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
        json.writeValue("r", rarity.name());
        json.writeValue("st", stats);
    }

    @Override
    public void read(Json json, JsonValue jsonValue) {
        type = GearType.valueOf(jsonValue.getString("t").toUpperCase(Locale.ENGLISH));
        level = jsonValue.getInt("l");
        starCount = jsonValue.getInt("s");
        skin = jsonValue.getString("sk");
        rarity = Rarity.valueOf(jsonValue.getString("r".toUpperCase(Locale.ENGLISH)));
        stats = json.readValue(Stats.class, jsonValue.get("st"));
    }
}
