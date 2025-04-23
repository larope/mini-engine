package com.bootcamp.demo.managers;

import com.badlogic.gdx.utils.ObjectMap;
import com.bootcamp.demo.data.game.GearType;
import com.bootcamp.demo.data.save.GearSaveData;
import com.bootcamp.demo.data.save.SaveData;
import com.bootcamp.demo.data.save.Stat;
import com.bootcamp.demo.data.save.StatEntry;

public class StatManager {
    private SaveData data;
    private final ObjectMap<Stat, Float> stats = new ObjectMap<>();

    public void setData(SaveData data) {
        this.data = data;

        for (ObjectMap.Entry<GearType, GearSaveData> gear : data.getGearsSaveData().getGears()) {
            for (ObjectMap.Entry<Stat, StatEntry> entry : gear.value.getStats().getValues()) {
                if(!stats.containsKey(entry.key)){
                    stats.put(entry.key, 0f);
                }
                float statEntry = stats.get(entry.key);
                statEntry += entry.value.getValue();

                stats.put(entry.key, statEntry);
            }
        }
    }

    public float getStatCombined(Stat stat) {
        return stats.get(stat);
    }

    public ObjectMap<Stat, Float> getAllStatsCombined() {
        ObjectMap<Stat, Float> statsCopy = new ObjectMap<>();

        for (Stat stat : Stat.values()) {
            statsCopy.put(stat, getStatCombined(stat));
        }

        return statsCopy;
    }

    public int getPower(){
        int power = 0;

        for (Stat stat : Stat.values()) {
            if(stat.getDefaultType() == Stat.StatType.ADDITIVE) {
                power += stats.get(stat);
            }
            else if(stat.getDefaultType() == Stat.StatType.MULTIPLICATIVE) {
                power *= (int) (1 + stats.get(stat)/100);
            }
        }

        return power;
    }
}
