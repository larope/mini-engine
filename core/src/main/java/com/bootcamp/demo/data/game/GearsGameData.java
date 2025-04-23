package com.bootcamp.demo.data.game;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectMap;
import com.badlogic.gdx.utils.XmlReader;
import lombok.Getter;

public class GearsGameData implements IGameData {
    @Getter
    private final ObjectMap<String, GearGameData> gears = new ObjectMap<>();

    @Override
    public void load (XmlReader.Element rootXml) {
        gears.clear();

        final Array<XmlReader.Element> gearXML = rootXml.getChildrenByName("gear");

        for (XmlReader.Element tacticalXml : gearXML) {
            final GearGameData data = new GearGameData();
            data.load(tacticalXml);
            gears.put(data.getName(), data);
        }
    }
}
