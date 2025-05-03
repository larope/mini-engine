package com.bootcamp.demo.data.save.stats;

import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.utils.ObjectMap;

import java.util.Random;

public class Stats implements Json.Serializable {
    ObjectMap<Stat, StatEntry> values = new ObjectMap<>();

    public ObjectMap<Stat, StatEntry> getValues(){
        if(!isFilled()){
            setDefaults();
        }

        return values;
    }

    @Override
    public void write(Json json) {
        for (ObjectMap.Entry<Stat, StatEntry> stat : values) {
            json.writeValue(stat.key.name(), stat.value);
        }
    }

    @Override
    public void read(Json json, JsonValue jsonValue) {
        for (JsonValue entry = jsonValue.child; entry != null; entry = entry.next) {
            String key = entry.name();

            Stat stat = Stat.valueOf(key);
            StatEntry statEntry = json.readValue(StatEntry.class, entry);
            values.put(stat, statEntry);
        }
    }

    public Stats plus(Stats other){
        for (ObjectMap.Entry<Stat, StatEntry> stat : values) {
            stat.value.setValue(stat.value.getValue() + other.values.get(stat.key).getValue());
        }

        return this;
    }

    public boolean isFilled(){
        return values.size == Stat.values().length;
    }

    public void setDefaults(){
        for (Stat stat : Stat.values()){
            if(values.get(stat) != null){
                continue;
            }
            StatEntry value = new StatEntry();

            value.setStat(stat);
            value.setValue(0);

            value.setStatType(stat.getDefaultType());

            values.put(stat, value);
        }
    }
}
