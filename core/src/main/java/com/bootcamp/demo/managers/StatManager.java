package com.bootcamp.demo.managers;

import com.badlogic.gdx.utils.ObjectMap;
import com.bootcamp.demo.data.game.GearType;
import com.bootcamp.demo.data.save.GearSaveData;
import com.bootcamp.demo.data.save.SaveData;
import com.bootcamp.demo.data.save.Stat;
import com.bootcamp.demo.data.save.StatEntry;
import lombok.Getter;

public class StatManager {
    private SaveData data;

    private final ObjectMap<Stat, Float> additiveStats = new ObjectMap<>();
    private final ObjectMap<Stat, Float> multiplicativeStats = new ObjectMap<>();

    public ObjectMap<Stat, Float> getAdditiveStats() {
        recalculate();
        return new ObjectMap<>(additiveStats);
    }

    public ObjectMap<Stat, Float> getMultiplicativeStats() {
        recalculate();
        return new ObjectMap<>(multiplicativeStats);
    }

    public void setData(SaveData data) {
        this.data = data;

        recalculate();
    }

    private void recalculate() {
        for (Stat stat : Stat.values()) {
            additiveStats.put(stat, 1f);
            multiplicativeStats.put(stat, 100f);
        }

        for (ObjectMap.Entry<GearType, GearSaveData> gear : data.getGearsSaveData().getGears()) {
            for (ObjectMap.Entry<Stat, StatEntry> entry : gear.value.getStats().getValues()) {
                if(entry.value.getStatType() == Stat.Type.ADDITIVE){
                    additiveStats.put(entry.key, additiveStats.get(entry.key)+entry.value.getValue());
                }
                if(entry.value.getStatType() == Stat.Type.MULTIPLICATIVE){
                    multiplicativeStats.put(entry.key, multiplicativeStats.get(entry.key)+entry.value.getValue());
                }
            }
        }
    }

    public float getStatCombined(Stat stat) {
        recalculate();
        if(stat.getDefaultType() == Stat.Type.MULTIPLICATIVE && stat.isDefaultRequired()){
            return multiplicativeStats.get(stat)-100f;
        }
        return additiveStats.get(stat) * multiplicativeStats.get(stat)/100;
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

        for (ObjectMap.Entry<Stat, Float> stat : getAllStatsCombined()) {
            power += stat.value;
        }

        return power;
    }
}
