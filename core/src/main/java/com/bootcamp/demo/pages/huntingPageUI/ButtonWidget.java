package com.bootcamp.demo.pages.huntingPageUI;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.bootcamp.demo.engine.Squircle;

import static com.bootcamp.demo.pages.huntingPageUI.HuntingPageUI.defaultRectSize;

public class ButtonWidget extends Table {
    public Table construct(){
        Table upgradeButton = new Table().background(Squircle.SQUIRCLE_35.getDrawable(Color.valueOf("#9A8158")));//#DDB46B
        Table upgradeButtonInner = new Table().background(Squircle.SQUIRCLE_35.getDrawable(Color.valueOf("#DDB46B")));
        upgradeButton.pad(10,10,50,10).add(upgradeButtonInner).grow();

        Table huntingButton = new Table().background(Squircle.SQUIRCLE_35.getDrawable(Color.valueOf("#6D9C56")));//#99D87F
        Table huntingButtonInner = new Table().background(Squircle.SQUIRCLE_35.getDrawable(Color.valueOf("#99D87F")));
        huntingButton.pad(10,10,50,10).add(huntingButtonInner).grow();

        Table autoHuntingButton = new Table().background(Squircle.SQUIRCLE_35.getDrawable(Color.valueOf("#7D7D7D")));//#BBBAB9
        Table autoHuntingButtonInner = new Table().background(Squircle.SQUIRCLE_35.getDrawable(Color.valueOf("#BBBAB9")));
        autoHuntingButton.pad(10,10,50,10).add(autoHuntingButtonInner).grow();

        defaults().growX().space(30).height(defaultRectSize);
        add(upgradeButton);
        add(huntingButton);
        add(autoHuntingButton);
        return this;
    }
}
