package com.bootcamp.demo.pages.huntingPageUI;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.bootcamp.demo.engine.FontManager;
import com.bootcamp.demo.engine.Squircle;
import com.bootcamp.demo.engine.widgets.WidgetsContainer;
import com.bootcamp.demo.localization.GameFont;
import com.bootcamp.demo.util.serviceLocator.ServiceLocator;

import static com.bootcamp.demo.pages.huntingPageUI.HuntingPageUI.defaultRectSize;

public class AccessoriesWidget extends Table {
    private FontManager fontManager;

    public Table construct(){
        fontManager = ServiceLocator.get(FontManager.class);

        add(constructEquipmentSegment()).expandX().left();
        add(constructSecondaryEquipmentSegment()).expandX();

        return this;
    }
    private Table constructSecondaryEquipmentSegment(){
        WidgetsContainer<Table> secondaryGear = new WidgetsContainer<>(2);
        secondaryGear.background(Squircle.SQUIRCLE_35.getDrawable(Color.valueOf("#C4B7AE"))).pad(10);
        secondaryGear.defaults().space(10).grow();

        for(int i = 0; i < 4; i++){
            Table item = new Table()
                .background(Squircle.SQUIRCLE_35.getDrawable(Color.valueOf("#7F7268")))
                .pad(10);
            Table innerPart = new Table().background(Squircle.SQUIRCLE_35.getDrawable(Color.valueOf("#B19985")));
            item.add(innerPart).grow();
            secondaryGear.add(item);
        }
        Table flag = new Table()
            .background(Squircle.SQUIRCLE_35.getDrawable(Color.valueOf("#84786E")))
            .pad(10);
        Table flagInner = new Table().background(Squircle.SQUIRCLE_35.getDrawable(Color.valueOf("#C2B8AF")));
        flag.add(flagInner).grow();

        Table leftSegment = new Table();
        leftSegment.defaults().space(25).size(defaultRectSize);
        leftSegment.add(secondaryGear);
        leftSegment.row();
        leftSegment.add(flag);

        Table petSegment = new Table()
            .background(Squircle.SQUIRCLE_35.getDrawable(Color.valueOf("#84786E")))
            .pad(10);
        Table petSegmentInnerPart = new Table().background(Squircle.SQUIRCLE_35.getDrawable(Color.valueOf("#C2B8AF")));
        petSegment.add(petSegmentInnerPart).grow();

        Table segment = new Table();
        segment.add(leftSegment).spaceRight(25);
        segment.add(petSegment).growY().width(defaultRectSize);
        return segment;
    }

    private Table getSetIndicator(){
        Table setIndicator = new Table()
            .background(Squircle.SQUIRCLE_35.getDrawable(Color.valueOf("#A29890")));
        final BitmapFont font = fontManager.getLabelStyle(GameFont.BOLD_20).font;
        final Label setIndicatorText = new Label("Nepolniy nabor", new Label.LabelStyle(font, Color.valueOf("#524B40")));
        setIndicator.add(setIndicatorText);

        return setIndicator;
    }
    private Table constructEquipmentSegment(){
        WidgetsContainer<Table> accessories = new WidgetsContainer<>(3);
        accessories.defaults().size(defaultRectSize)
            .space(20);

        for(int i = 0; i < 6; i++){
            Table item = new Table()
                .background(Squircle.SQUIRCLE_35.getDrawable(Color.valueOf("#7F7268")))
                .pad(10);
            Table innerPart = new Table().background(Squircle.SQUIRCLE_35.getDrawable(Color.valueOf("#B19985")));
            item.add(innerPart).grow();
            accessories.add(item);
        }

        Table segment = new Table()
            .background(Squircle.SQUIRCLE_35.getDrawable(Color.valueOf("#CAC9C7")))
            .pad(15,30,15,30);

        segment.add(getSetIndicator()).growX().height(50);
        segment.row();
        segment.add(accessories).spaceTop(15);

        return segment;
    }
}
