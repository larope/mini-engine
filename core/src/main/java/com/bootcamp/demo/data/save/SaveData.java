package com.bootcamp.demo.data.save;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectMap;
import com.bootcamp.demo.data.game.GearType;
import lombok.Getter;

public class SaveData {
    @Getter
    private final TacticalsSaveData tacticalsSaveData = new TacticalsSaveData();
    @Getter
    private final GearsSaveData gearsSaveData = new GearsSaveData();

    private final ObjectMap<Stat, StatEntry> stats = new ObjectMap<>();

    public SaveData() {
        tacticalsSaveData.setDefaults();
        gearsSaveData.setDefaults();

        for (ObjectMap.Entry<GearType, GearSaveData> gear : gearsSaveData.getGears()) {
            for (ObjectMap.Entry<Stat, StatEntry> entry : gear.value.getStats().getValues()) {
                StatEntry statEntry = stats.get(entry.key);
                statEntry.setValue(statEntry.getValue() + entry.value.getValue());

                stats.put(entry.key, statEntry);
            }
        }
    }

    public ObjectMap<Stat, String> getAllStats(){
        ObjectMap<Stat, String> allStats = new ObjectMap<>();
        for (Stat stat : Stat.values()) {
            allStats.put(stat, stats.ge);
        }

        return allStats;
    }
}
