package com.bootcamp.demo.data.game;

import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.XmlReader;
import com.bootcamp.demo.engine.Resources;
import lombok.Getter;

public class TacticalGameData implements IGameData {
    @Getter
    private Tactical tactical;
    @Getter
    private Drawable icon;

    @Override
    public void load (XmlReader.Element rootXml) {
        tactical = Tactical.fromName(rootXml.getAttribute("name"));
        icon = Resources.getDrawable(rootXml.getAttribute("icon"));
    }
}
