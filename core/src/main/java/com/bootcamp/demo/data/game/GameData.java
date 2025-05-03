package com.bootcamp.demo.data.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.XmlReader;
import com.bootcamp.demo.data.game.gear.GearsGameData;
import com.bootcamp.demo.data.game.pet.PetsGameData;
import com.bootcamp.demo.data.game.tacticals.TacticalsGameData;
import lombok.Getter;

public class GameData {

    private final XmlReader xmlReader = new XmlReader();

    @Getter
    private final TacticalsGameData tacticalsGameData;

    @Getter
    private final GearsGameData gearsGameData;

    @Getter
    private final PetsGameData petsGameData;

    public GameData () {
        tacticalsGameData = new TacticalsGameData();
        gearsGameData = new GearsGameData();
        petsGameData = new PetsGameData();
    }

    public void load () {
        tacticalsGameData.load(xmlReader.parse(Gdx.files.internal("data/tacticals.xml")));
        gearsGameData.load(xmlReader.parse(Gdx.files.internal("data/gearSkins.xml")));
        petsGameData.load(xmlReader.parse(Gdx.files.internal("data/pets.xml")));
    }
}
