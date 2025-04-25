package com.bootcamp.demo.managers;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectMap;
import com.bootcamp.demo.data.game.GameData;
import com.bootcamp.demo.data.game.Rarity;
import com.bootcamp.demo.data.game.gear.GearGameData;
import com.bootcamp.demo.data.game.gear.GearType;
import com.bootcamp.demo.data.game.gear.GearsGameData;
import com.bootcamp.demo.data.game.tacticals.TacticalsGameData;
import com.bootcamp.demo.data.save.SaveData;
import com.bootcamp.demo.data.save.gear.GearSaveData;
import com.bootcamp.demo.data.save.gear.GearSkinSaveData;
import com.bootcamp.demo.data.save.stats.Stat;
import com.bootcamp.demo.data.save.stats.StatEntry;
import com.bootcamp.demo.data.save.stats.Stats;
import com.bootcamp.demo.data.save.tacticals.TacticalSaveData;

import java.util.Random;

public class RandomData {
    private final GearsGameData gearsGameData;
    private final TacticalsGameData tacticalsGameData;
    private final Random random;

    public RandomData() {
        gearsGameData = API.get(GameData.class).getGearsGameData();
        tacticalsGameData = API.get(GameData.class).getTacticalsGameData();
        random = new Random();
    }


    public Stats getRandomStats(Vector2 additiveRange, Vector2 multiplicativeRange){
        Stats stats = new Stats();

        for (Stat stat : Stat.values()){
            stats.getValues().put(stat, getRandomStat(additiveRange, multiplicativeRange, stat));
        }

        return stats;
    }
    public StatEntry getRandomStat(Vector2 additiveRange, Vector2 multiplicativeRange){
        return getRandomStat(additiveRange, multiplicativeRange, Stat.values()[random.nextInt(Stat.values().length)]);
    }
    public StatEntry getRandomStat(Vector2 additiveRange, Vector2 multiplicativeRange, Stat type){
        StatEntry stat = new StatEntry();
        stat.setStat(type);

        Stat.Type statType;

        if(!type.isDefaultRequired()){
            statType = Stat.Type.values()[random.nextInt(Stat.Type.values().length)];
        }
        else{
            statType = type.getDefaultType();
        }

        stat.setStatType(statType);

        if(statType == Stat.Type.ADDITIVE) {
            stat.setValue(random.nextFloat(additiveRange.x, additiveRange.y));
        }
        else if(statType == Stat.Type.MULTIPLICATIVE) {
            stat.setValue(random.nextFloat(multiplicativeRange.x, multiplicativeRange.y));
        }

        return stat;
    }

    public GearSaveData getRandomGear(GearType type, int minLevel, int maxLevel, int minStar, int maxStar) {
        GearSaveData data = new GearSaveData();

        data.setLevel(random.nextInt(maxLevel-minLevel) + minLevel);

        data.setStarCount(random.nextInt(maxStar-minStar)+minStar);

        data.setType(type);

        data.setRarity(Rarity.getRandomRarity());

        ObjectMap<String, GearGameData> gears = gearsGameData.getGears().get(type);
        Array<String> availableSkins = gears.keys().toArray();

        Stats skinStats = new Stats();
        skinStats.setDefaults();
        GearSkinSaveData skin = new GearSkinSaveData();
        skin.setStats(skinStats);
        skin.setName(availableSkins.get(random.nextInt(availableSkins.size)));
        skin.setType(type);
        API.get(SaveData.class).getGearSkins().addSkin(skin);
        data.setSkin(skin);

        return data;
    }
    public TacticalSaveData getRandomTactical(int minLevel, int maxLevel, int minStar, int maxStar, int minCount, int maxCount) {
        TacticalSaveData data = new TacticalSaveData();
        Array<String> names = tacticalsGameData.getTacticals().keys().toArray();

        data.setName(names.get(random.nextInt(names.size)));
        data.setLevel(random.nextInt(maxLevel-minLevel) + minLevel);
        data.setCount(random.nextInt(maxStar-minStar)+minStar);

        data.setRarity(Rarity.getRandomRarity());


        return data;
    }
}
