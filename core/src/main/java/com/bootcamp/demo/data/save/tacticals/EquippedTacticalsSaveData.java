package com.bootcamp.demo.data.save.tacticals;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.IntMap;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.utils.ObjectMap;
import com.bootcamp.demo.data.save.SaveData;
import com.bootcamp.demo.managers.API;
import com.bootcamp.demo.managers.RandomData;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

public class EquippedTacticalsSaveData implements Json.Serializable {
    public static final int TACTICALS_COUNT = 4;
    @Getter
    private Set<String> tacticals = new HashSet<>();
    private TacticalsSaveData tacticalsSaveData;


    private TacticalsSaveData getSaveData() {
        if(tacticalsSaveData == null){
             tacticalsSaveData = API.get(SaveData.class).getTacticalsSaveSata();
        }

        return tacticalsSaveData;
    }

    private TacticalSaveData getTacticalAsData(String name){
        return getSaveData().getTacticals().get(name);
    }

    public IntMap<TacticalSaveData> getTacticalsAsData(){
        final IntMap<TacticalSaveData> data = new IntMap<>();

        int i = 0;
        for (String tactical : tacticals) {
            data.put(i++, getTacticalAsData(tactical));
        }

        return data;
    }

    @Override
    public void write(Json json) {
        int i = 0;
        for (String tactical : tacticals) {
            json.writeValue(String.valueOf(i++), tactical);
        }
    }

    @Override
    public void read(Json json, JsonValue jsonValue) {
        for (int i = 0; i < TACTICALS_COUNT; i++) {
            if(!jsonValue.has(String.valueOf(i))){
                break;
            }
            tacticals.add(jsonValue.getString(String.valueOf(i)));
        }
    }

    public void setDefaults(){
        for (int i = 0; i < TACTICALS_COUNT; i++) {
            final TacticalSaveData defaultTactical = API.get(RandomData.class).getRandomTactical(3, 8, 3, 7);
            defaultTactical.setStats(API.get(RandomData.class).getRandomStats(new Vector2(20, 500), new Vector2(0,1)));

            getSaveData().addTactical(defaultTactical);
            tacticals.add(defaultTactical.getName());
        }
    }
}
