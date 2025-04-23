package com.bootcamp.demo.data.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.XmlReader;
import lombok.Getter;

public class GameData {

    private final XmlReader xmlReader = new XmlReader();

    @Getter
    private final TacticalsGameData tacticalsGameData;

    @Getter
    private final GearsGameData gearsGameData;

    public GameData () {
        tacticalsGameData = new TacticalsGameData();
        gearsGameData = new GearsGameData();
    }

    public void load () {
        tacticalsGameData.load(xmlReader.parse(Gdx.files.internal("data/tacticals.xml")));
        gearsGameData.load(xmlReader.parse(Gdx.files.internal("data/GearSkins.xml")));
    }
}
