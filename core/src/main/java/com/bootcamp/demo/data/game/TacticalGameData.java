package com.bootcamp.demo.data.game;

import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.XmlReader;
import com.bootcamp.demo.engine.Resources;
import lombok.Getter;

public class TacticalGameData implements IGameData {
    @Getter
    private String name;
    @Getter
    private String drawablePath;

    @Override
    public void load (XmlReader.Element rootXml) {
        name = rootXml.getAttribute("name");
        drawablePath = rootXml.getAttribute("icon");
    }

    public Drawable getDrawable () {
        return Resources.getDrawable(drawablePath);
    }
}
