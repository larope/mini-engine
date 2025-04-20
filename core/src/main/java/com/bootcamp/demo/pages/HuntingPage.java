package com.bootcamp.demo.pages;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;
import com.bootcamp.demo.data.save.SaveData;
import com.bootcamp.demo.data.save.Stat;
import com.bootcamp.demo.data.save.StatsSaveData;
import com.bootcamp.demo.engine.FontManager;
import com.bootcamp.demo.engine.Squircle;
import com.bootcamp.demo.engine.widgets.BorderedTable;
import com.bootcamp.demo.engine.widgets.OffsetButton;
import com.bootcamp.demo.engine.widgets.WidgetsContainer;
import com.bootcamp.demo.localization.GameFont;
import com.bootcamp.demo.managers.API;
import com.bootcamp.demo.pages.core.APage;
import com.bootcamp.demo.util.serviceLocator.ServiceLocator;

import java.util.Map;

public class HuntingPage extends APage {
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

    private static class StatsWidget extends Table {
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
            final BorderedTable menuButton = new BorderedTable(
                Squircle.SQUIRCLE_35.getDrawable(Color.valueOf("#F1E6DC")),
                Squircle.SQUIRCLE_35_BORDER.getDrawable(Color.valueOf("#847467"))
            );

            return menuButton;
        }
        private Table constructStatsSegment(){
            final WidgetsContainer<Table> statsContainer = new WidgetsContainer<>(3);
            statsContainer.pad(0, 20, 0, 20);

            statsContainer.defaults().growX().space(10, 40, 10, 40);


            StatsSaveData stats = API.get(SaveData.class).getStatsSaveData();

            for (Map.Entry<Stat, Float> entry : stats.getStats().entrySet()) {
                float value = entry.getValue();
                Stat stat = entry.getKey();
                statsContainer.add(createStat(stat.getTitle(), Float.toString(value)));
            }

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
    private static class ButtonWidget extends Table {
        public Table construct(){
            final OffsetButton upgradeButton = new OffsetButton(OffsetButton.Style.YELLOW_35);
            final OffsetButton huntingButton = new OffsetButton(OffsetButton.Style.GREEN_35);
            final OffsetButton autoHuntingButton = new OffsetButton(OffsetButton.Style.GRAY_35);

            upgradeButton.setOffset(35);
            huntingButton.setOffset(35);
            autoHuntingButton.setOffset(35);

            defaults().growX().space(30).height(defaultRectSize);
            add(upgradeButton);
            add(huntingButton);
            add(autoHuntingButton);
            return this;
        }
    }
    private static class AccessoriesWidget extends Table {
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
                BorderedTable item = new BorderedTable(
                    Squircle.SQUIRCLE_35.getDrawable(Color.valueOf("#B19985")),
                    Squircle.SQUIRCLE_35_BORDER.getDrawable(Color.valueOf("#7F7268"))
                );

                secondaryGear.add(item);
            }

            BorderedTable flag = new BorderedTable(
                Squircle.SQUIRCLE_35.getDrawable(Color.valueOf("#C2B8AF")),
                Squircle.SQUIRCLE_35_BORDER.getDrawable(Color.valueOf("#84786E"))
            );
            Table leftSegment = new Table();
            leftSegment.defaults().space(25).size(defaultRectSize);
            leftSegment.add(secondaryGear);
            leftSegment.row();
            leftSegment.add(flag);

            BorderedTable petSegment = new BorderedTable(
                Squircle.SQUIRCLE_35.getDrawable(Color.valueOf("#C2B8AF")),
                Squircle.SQUIRCLE_35_BORDER.getDrawable(Color.valueOf("#84786E"))
            );
            Table segment = new Table();
            segment.add(leftSegment).spaceRight(25);
            segment.add(petSegment).growY().width(defaultRectSize);
            return segment;
        }

        private Table getSetIndicator(){
            Table setIndicator = new Table()
                .background(Squircle.SQUIRCLE_15.getDrawable(Color.valueOf("#A29890")));
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

                BorderedTable item = new BorderedTable(
                    Squircle.SQUIRCLE_35.getDrawable(Color.valueOf("#B19985")),
                    Squircle.SQUIRCLE_35_BORDER.getDrawable(Color.valueOf("#7F7268"))
                );
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
}
