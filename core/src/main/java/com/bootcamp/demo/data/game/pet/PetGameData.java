package com.bootcamp.demo.data.game.pet;

import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.XmlReader;
import com.bootcamp.demo.data.game.IGameData;
import com.bootcamp.demo.data.game.gear.GearType;
import com.bootcamp.demo.engine.Resources;
import lombok.Getter;
import lombok.Setter;

import java.util.Locale;

public class PetGameData implements IGameData {
    @Getter @Setter
    String name;
    @Getter @Setter
    String drawablePath;

    @Override
    public void load(XmlReader.Element rootXml) {
        name = rootXml.getAttribute("name");
        drawablePath = rootXml.getAttribute("icon");
    }

    public Drawable getDrawable() {
        return Resources.getDrawable(drawablePath);
    }
}
