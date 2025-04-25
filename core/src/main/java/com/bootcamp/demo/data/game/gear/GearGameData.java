package com.bootcamp.demo.data.game.gear;

import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.XmlReader;
import com.bootcamp.demo.data.game.IGameData;
import com.bootcamp.demo.engine.Resources;
import lombok.Getter;
import java.util.Locale;

public class GearGameData implements IGameData {
    @Getter
    private GearType type;
    @Getter
    private String name;
    @Getter
    private String drawablePath;

    @Override
    public void load (XmlReader.Element rootXml) {
        name = rootXml.getAttribute("name");
        drawablePath = rootXml.getAttribute("icon");
        type = GearType.valueOf(rootXml.getAttribute("type").toUpperCase(Locale.ENGLISH));
    }

    public Drawable getDrawable () {
        return Resources.getDrawable(drawablePath);
    }
}
