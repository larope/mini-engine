package com.bootcamp.demo.pages.huntingPageUI;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.bootcamp.demo.engine.Squircle;
import com.bootcamp.demo.pages.core.APage;

public class HuntingPageUI extends APage {
    static final int defaultRectSize = 225;

    @Override
    protected void constructContent(Table content) {
        StatsWidget statsWidget = new StatsWidget();
        AccessoriesWidget accessoriesWidget = new AccessoriesWidget();
        ButtonWidget buttonWidget = new ButtonWidget();

        Table bottomUI = new Table()
            .background(Squircle.SQUIRCLE_35_TOP.getDrawable(Color.valueOf("#fbeae0")))
            .pad(30);

        bottomUI.add(statsWidget.construct()).growX();
        bottomUI.row();
        bottomUI.add(accessoriesWidget.construct()).growX().space(30);
        bottomUI.row();
        bottomUI.add(buttonWidget.construct()).growX();


        Table topUI = new Table();

        content.add(topUI).grow();
        content.row();
        content.add(constructPowerIndicatorSegment()).expandX().height(100).width(500);
        content.row();
        content.add(bottomUI).growX();
    }

    private Table constructPowerIndicatorSegment(){
        Table innerSegment = new Table()
            .background(Squircle.SQUIRCLE_35_TOP.getDrawable(Color.valueOf("#AE9E91")));

        Table segment = new Table()
            .background(Squircle.SQUIRCLE_35_TOP.getDrawable(Color.valueOf("#fbeae0")))
            .pad(10).padBottom(0);

        segment.add(innerSegment).grow();
        return segment;
    }
}
