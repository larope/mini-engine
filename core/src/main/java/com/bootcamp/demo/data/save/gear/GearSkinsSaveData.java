package com.bootcamp.demo.data.save.gear;

import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.utils.ObjectMap;
import com.bootcamp.demo.data.game.gear.GearType;
import lombok.Getter;

public class GearSkinsSaveData implements Json.Serializable {
    @Getter
    private final ObjectMap<GearType, Array<GearSkinSaveData>> availableSkins = new ObjectMap<>();

    public void addSkin(GearSkinSaveData skin){
        if(availableSkins.get(skin.type) == null){
            availableSkins.put(skin.type, new Array<>());
        }

        availableSkins.get(skin.type).add(skin);
    }

    public GearSkinSaveData getSkin(GearType type, String name){
        for(GearSkinSaveData skin : availableSkins.get(type)){
            if(skin.name.equals(name)){
                return skin;
            }
        }

        return null;
    }
    @Override
    public void write(Json json) {
        for (ObjectMap.Entry<GearType, Array<GearSkinSaveData>> skins : availableSkins) {
            json.writeValue(skins.key.name(), skins.value, Array.class, GearSkinSaveData.class);
        }
    }

    @Override
    public void read(Json json, JsonValue jsonValue) {
        for (GearType type : GearType.values()) {
            availableSkins.put(type, new Array<>());
        }

        for (JsonValue entry = jsonValue.child; entry != null; entry = entry.next) {
            GearType type = GearType.valueOf(entry.name);
            Array<GearSkinSaveData> skins = json.readValue(Array.class, GearSkinSaveData.class, entry);
            availableSkins.put(type, skins);
        }
    }
}
