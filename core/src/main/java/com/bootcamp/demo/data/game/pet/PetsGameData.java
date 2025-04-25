package com.bootcamp.demo.data.game.pet;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectMap;
import com.badlogic.gdx.utils.XmlReader;
import com.bootcamp.demo.data.game.IGameData;
import lombok.Getter;

public class PetsGameData implements IGameData {
    @Getter
    private final ObjectMap<String, PetGameData> pets = new ObjectMap<>();

    @Override
    public void load(XmlReader.Element rootXml) {
        pets.clear();

        final Array<XmlReader.Element> gearXML = rootXml.getChildrenByName("pet");

        for (XmlReader.Element tacticalXml : gearXML) {
            final PetGameData data = new PetGameData();
            data.load(tacticalXml);
            pets.put(data.name, data);
        }
    }
}
