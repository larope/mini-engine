package com.bootcamp.demo.pages;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.IntMap;
import com.badlogic.gdx.utils.ObjectMap;
import com.bootcamp.demo.data.save.*;
import com.bootcamp.demo.engine.FontManager;
import com.bootcamp.demo.engine.Labels;
import com.bootcamp.demo.engine.Squircle;
import com.bootcamp.demo.engine.widgets.BorderedTable;
import com.bootcamp.demo.engine.widgets.OffsetButton;
import com.bootcamp.demo.engine.widgets.WidgetsContainer;
import com.bootcamp.demo.localization.GameFont;
import com.bootcamp.demo.managers.API;
import com.bootcamp.demo.pages.core.APage;
import com.bootcamp.demo.util.NumberFormatter;
import com.bootcamp.demo.util.serviceLocator.ServiceLocator;

import javax.lang.model.util.SimpleAnnotationValueVisitor6;

public class HuntingPage extends APage {
    static final int defaultRectSize = 225;

    StatsWidget statsWidget;
    AccessoriesWidget accessoriesWidget;
    ButtonWidget buttonWidget;
    PowerWidget powerWidget;

    SaveData data;

    public void setData(SaveData saveData) {
        data = saveData;

        powerWidget = new PowerWidget();
        accessoriesWidget = new AccessoriesWidget();
        buttonWidget = new ButtonWidget();
        statsWidget = new StatsWidget();

        powerWidget.setData(50);
        statsWidget.setData(API.get(SaveData.class).getStatsSaveData().getStats());
        accessoriesWidget.setData(saveData.getGearsSaveData().getGears());
    }

    @Override
    protected void constructContent(Table content) {
        setData(API.get(SaveData.class));

        final Table topUI = new Table();

        content.add(topUI).grow();
        content.row();
        content.add(powerWidget.construct()).expandX().height(100).width(600);
        content.row();
        content.add(constructMainUi()).growX();
    }

    private Table constructMainUi(){
        final Table mainUi = new Table()
            .background(Squircle.SQUIRCLE_35_TOP.getDrawable(Color.valueOf("#fbeae0")))
            .pad(30);

        mainUi.add(statsWidget.construct()).growX();
        mainUi.row();
        mainUi.add(accessoriesWidget.construct()).growX().space(30);
        mainUi.row();
        mainUi.add(buttonWidget.construct()).growX();

        return mainUi;
    }

    private static class PowerWidget extends BorderedTable {
        private int power = -42;

        public Table construct(){
            if(!powerAvailable()){
                throw new RuntimeException("Power not available");
            }

            background(Squircle.SQUIRCLE_35_TOP.getDrawable(Color.valueOf("#AE9E91")));
            setBorderDrawable(Squircle.SQUIRCLE_35_BORDER_TOP.getDrawable(Color.valueOf("#fbeae0")));

            final Label powerLabel = Labels.make(GameFont.BOLD_32, Color.WHITE, String.valueOf(NumberFormatter.formatToShortForm(power)));
            add(powerLabel);
            return this;
        }

        private boolean powerAvailable() {
            return power != -42;
        }

        public void setData (int power){
            this.power = power;
        }
    }
    private static class StatsWidget extends Table {
        private ObjectMap<Stat, Float> stats = new ObjectMap<>();

        public void setData(ObjectMap<Stat, Float> stats){
            this.stats = stats;
        }

        public Table construct () {
            if(!statsAvailable()){
                throw new RuntimeException("Stats not available");
            }

            background(Squircle.SQUIRCLE_35.getDrawable(Color.valueOf("#AE9E91"))).pad(20);

            add(constructStatsSegment()).growX();
            add(constructMenuButton()).size(100).space(20);
            return this;
        }
        private Table constructMenuButton() {
            return new BorderedTable(
                Squircle.SQUIRCLE_35.getDrawable(Color.valueOf("#F1E6DC")),
                Squircle.SQUIRCLE_35_BORDER.getDrawable(Color.valueOf("#847467"))
            );
        }
        private Table constructStatsSegment (){
            final WidgetsContainer<Table> statsContainer = new WidgetsContainer<>(3);
            statsContainer.pad(0, 20, 0, 20)
                .defaults().growX().space(10, 40, 10, 40);

            for (ObjectMap.Entry<Stat, Float> entry : stats.entries()) {
                statsContainer.add(createStat(entry.key.getTitle(), entry.value.toString()));
            }

            return statsContainer;
        }
        private Table createStat(String name, String value){
            final Label statName = Labels.make(GameFont.BOLD_20, Color.valueOf("#524B40"), name);
            final Label statValue = Labels.make(GameFont.BOLD_20, Color.valueOf("#F9F5EE"), value);

            statValue.setAlignment(Align.right);

            final Table statWrapper = new Table();

            statWrapper.add(statName).growX().left();
            statWrapper.add(statValue).growX().right();

            return statWrapper;
        }

        private boolean statsAvailable(){
            return stats.size == Stat.values().length;
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

        public void setData (){}
    }
    private static class AccessoriesWidget extends Table {
        private static final int gearCount = 6;
        private IntMap<GearSaveData> gears = new IntMap<>();

        public Table construct(){
            assert gearsAvailable() : "Invalid number of gears";

            add(constructEquipmentSegment()).expandX().left();
            add(constructSecondaryEquipmentSegment()).expandX();

            return this;
        }

        private Table constructSecondaryEquipmentSegment(){
            WidgetsContainer<Table> secondaryGear = new WidgetsContainer<>(2);
            secondaryGear.background(Squircle.SQUIRCLE_35.getDrawable(Color.valueOf("#C4B7AE"))).pad(10);
            secondaryGear.defaults().space(10).grow().uniform();

            IntMap<TacticalSaveData> tacticals = API.get(SaveData.class).getTacticalsSaveData().getTacticals();

            for (IntMap.Entry<TacticalSaveData> tactical : tacticals) {
                BorderedTable item = new BorderedTable(
                    Squircle.SQUIRCLE_35.getDrawable(Color.valueOf("#B19985")),
                    Squircle.SQUIRCLE_35_BORDER.getDrawable(Color.valueOf("#7F7268"))
                );
                Table tacticalIcon = new Table();
                tacticalIcon.setBackground(tactical.value.getName().getDrawable());
                item.add(tacticalIcon).growY();
                item.pad(10);
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
            final Table setIndicator = new Table()
                .background(Squircle.SQUIRCLE_15.getDrawable(Color.valueOf("#A29890")));

            final Label setIndicatorText = Labels.make(GameFont.BOLD_20, Color.valueOf("#524B40"), "Nepolniy Nabor");
            setIndicator.add(setIndicatorText);

            return setIndicator;
        }
        private Table constructEquipmentSegment(){
            final WidgetsContainer<Table> gearContainer = new WidgetsContainer<>(3);
            gearContainer.defaults().size(defaultRectSize)
                .space(20);


            for (IntMap.Entry<GearSaveData> gear : gears) {
                final BorderedTable item = new BorderedTable(
                    Squircle.SQUIRCLE_35.getDrawable(Color.valueOf("#B19985")),
                    Squircle.SQUIRCLE_35_BORDER.getDrawable(Color.valueOf("#7F7268"))
                );

                final Table icon = new Table();
                icon.background(gear.value.getName().getDrawable())
                    .add(icon).grow();

                gearContainer.add(item);
            }

            Gdx.app.log("DEBUG", "qaq");

            Table segment = new Table()
                .background(Squircle.SQUIRCLE_35.getDrawable(Color.valueOf("#CAC9C7")))
                .pad(15,30,15,30);

            segment.add(getSetIndicator()).growX().height(50);
            segment.row();
            segment.add(gearContainer).spaceTop(15);

            return segment;
        }

        private boolean gearsAvailable() {
            return gears.size == gearCount;
        }

        public void setData(IntMap<GearSaveData> gears){
            this.gears = gears;
        }
    }
}
