package com.bootcamp.demo.data.game.gear;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectMap;
import com.badlogic.gdx.utils.XmlReader;
import com.bootcamp.demo.data.game.IGameData;
import lombok.Getter;

public class GearsGameData implements IGameData {
    @Getter
    private final ObjectMap<GearType, ObjectMap<String, GearGameData>> gears = new ObjectMap<>();

    @Override
    public void load (XmlReader.Element rootXml) {
        gears.clear();

        final Array<XmlReader.Element> gearXML = rootXml.getChildrenByName("gear");

        for (GearType value : GearType.values()) {
            gears.put(value, new ObjectMap<>());
        }

        for (XmlReader.Element tacticalXml : gearXML) {
            final GearGameData data = new GearGameData();
            data.load(tacticalXml);
            gears.get(data.getType()).put(data.getName(), data);
        }
    }
}
