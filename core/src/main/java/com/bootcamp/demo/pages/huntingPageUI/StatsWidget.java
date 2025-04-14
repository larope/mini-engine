package com.bootcamp.demo.pages.huntingPageUI;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;
import com.bootcamp.demo.engine.FontManager;
import com.bootcamp.demo.engine.Squircle;
import com.bootcamp.demo.localization.GameFont;
import com.bootcamp.demo.util.serviceLocator.ServiceLocator;

public class StatsWidget extends Table {
    private FontManager fontManager;

    public Table construct() {
        fontManager = ServiceLocator.get(FontManager.class);

        background(Squircle.SQUIRCLE_35.getDrawable(Color.valueOf("#AE9E91")));
        pad(20);

        add(constructStatsSegment()).growX();
        add(constructMenuButtonSegment()).size(100).space(20);
        return this;
    }

    private Table constructMenuButtonSegment() {
        Table innerPart = new Table();
        innerPart.background(Squircle.SQUIRCLE_35.getDrawable(Color.valueOf("#F1E6DC")));

        Table segment = new Table();
        // GOVNOCODE right there, I don't know how to make border appear ahead of inner part, tried z-index btw
        segment.background(Squircle.SQUIRCLE_35_BORDER.getDrawable(Color.valueOf("#847467")))
            .pad(7);
        segment.add(innerPart).grow();

        return segment;
    }
    private Table constructStatsSegment(){
        final Table statsContainer = new Table();
        statsContainer.pad(0, 20, 0, 20);

        statsContainer.defaults().growX().space(10, 40, 10, 40);

        statsContainer.add(createStat("OZ:", "2.4K"));
        statsContainer.add(createStat("ATK:", "928"));
        statsContainer.add(createStat("UVOROT:", "0%"));
        statsContainer.row();
        statsContainer.add(createStat("KOMBO:", "1.24%"));
        statsContainer.add(createStat("KRIT:", "0%"));
        statsContainer.add(createStat("OGLUSH:", "1.15%"));
        statsContainer.row();
        statsContainer.add(createStat("REGEN:", "1.15%"));
        statsContainer.add(createStat("KRAJA:", "2.3%"));
        statsContainer.add(createStat("OTRAVA:", "0.5%"));

        Table segment = new Table();
        segment.add(statsContainer).growX();

        return segment;
    }

    private Table createStat(String name, String value){
        final BitmapFont font = fontManager.getLabelStyle(GameFont.BOLD_20).font;

        final Label statName = new Label(name, new Label.LabelStyle(font, Color.valueOf("#524B40")));
        final Label statValue = new Label(value, new Label.LabelStyle(font, Color.valueOf("#F9F5EE")));
        statValue.setAlignment(Align.right);

        Table stat = new Table();

        stat.add(statName).growX().left();
        stat.add(statValue).growX().right();

        return stat;
    }
}
