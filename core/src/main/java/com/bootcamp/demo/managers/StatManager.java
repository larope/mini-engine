package com.bootcamp.demo.managers;

import com.badlogic.gdx.utils.IntMap;
import com.badlogic.gdx.utils.ObjectMap;
import com.bootcamp.demo.data.game.gear.GearType;
import com.bootcamp.demo.data.save.gear.GearSaveData;
import com.bootcamp.demo.data.save.SaveData;
import com.bootcamp.demo.data.save.stats.Stat;
import com.bootcamp.demo.data.save.stats.StatEntry;
import com.bootcamp.demo.data.save.stats.Stats;
import com.bootcamp.demo.data.save.tacticals.TacticalSaveData;

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

        for (IntMap.Entry<TacticalSaveData> tactical : data.getTacticalsSaveSata().getTacticals()) {
            addStats(tactical.value.getStats());
        }

        for (ObjectMap.Entry<GearType, GearSaveData> gear : data.getGearsSaveData().getGears()) {
            addStats(gear.value.getStats());
            addStats(data.getGearSkins().getSkin(gear.key, gear.value.getSkin()).getStats());
        }

    }

    private void addStats(Stats stats) {
        for (ObjectMap.Entry<Stat, StatEntry> entry : stats.getValues()) {
            if(entry.value.getStatType() == Stat.Type.ADDITIVE){
                additiveStats.put(entry.key, additiveStats.get(entry.key)+entry.value.getValue());
            }
            else if(entry.value.getStatType() == Stat.Type.MULTIPLICATIVE){
                multiplicativeStats.put(entry.key, multiplicativeStats.get(entry.key)+entry.value.getValue());
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
