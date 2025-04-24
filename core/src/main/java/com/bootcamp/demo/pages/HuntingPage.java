package com.bootcamp.demo.pages;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.IntMap;
import com.badlogic.gdx.utils.ObjectMap;
import com.bootcamp.demo.data.game.GameData;
import com.bootcamp.demo.data.game.GearType;
import com.bootcamp.demo.data.game.GearsGameData;
import com.bootcamp.demo.data.game.TacticalsGameData;
import com.bootcamp.demo.data.save.*;
import com.bootcamp.demo.dialogs.TestDialog;
import com.bootcamp.demo.dialogs.core.DialogManager;
import com.bootcamp.demo.engine.Labels;
import com.bootcamp.demo.engine.Squircle;
import com.bootcamp.demo.engine.widgets.BorderedTable;
import com.bootcamp.demo.engine.widgets.OffsetButton;
import com.bootcamp.demo.engine.widgets.PressableTable;
import com.bootcamp.demo.engine.widgets.WidgetsContainer;
import com.bootcamp.demo.localization.GameFont;
import com.bootcamp.demo.managers.API;
import com.bootcamp.demo.managers.StatManager;
import com.bootcamp.demo.pages.core.APage;
import com.bootcamp.demo.util.NumberFormatter;

public class HuntingPage extends APage {
    static final int defaultRectSize = 225;

    StatsWidget statsWidget;
    AccessoriesWidget accessoriesWidget;
    ButtonWidget buttonWidget;
    PowerWidget powerWidget;

    SaveData data;
    StatManager statManager;

    public void setData(SaveData saveData) {
        data = saveData;
        statManager = API.get(StatManager.class);
        statManager.setData(data);


        powerWidget = new PowerWidget();
        powerWidget.setData(statManager.getPower());

        accessoriesWidget = new AccessoriesWidget();
        accessoriesWidget.setData(saveData.getGearsSaveData().getGears(), saveData.getTacticalsSaveData().getTacticals());

        buttonWidget = new ButtonWidget();
        buttonWidget.setData();

        statsWidget = new StatsWidget();
        statsWidget.setData(statManager.getAllStatsCombined());
    }

    @Override
    protected void constructContent(Table content) {
        setData(API.get(SaveData.class));

        final Table topUI = new Table();

        content.add(topUI).grow();
        content.row();
        content.add(powerWidget.construct()).expandX().height(100).width(600);
        content.row();
        content.add(constructMainUI()).growX();
    }

    private Table constructMainUI(){
        final Table mainUI = new Table()
            .background(Squircle.SQUIRCLE_35_TOP.getDrawable(Color.valueOf("#fbeae0")))
            .pad(30);

        mainUI.defaults().growX();

        mainUI.add(statsWidget.construct());
        mainUI.row();
        mainUI.add(accessoriesWidget.construct()).space(30);
        mainUI.row();
        mainUI.add(buttonWidget.construct());

        return mainUI;
    }

    private static class PowerWidget extends BorderedTable {
        private int power = -42;

        public Table construct(){
            assert powerAvailable() : "Power not available: " + power;
            setTouchable(Touchable.disabled);
            background(Squircle.SQUIRCLE_35_TOP.getDrawable(Color.valueOf("#AE9E91")));
            setBorderDrawable(Squircle.SQUIRCLE_35_BORDER_TOP.getDrawable(Color.valueOf("#fbeae0")));

            final Label powerLabel = Labels.make(GameFont.BOLD_32, Color.WHITE, String.valueOf(NumberFormatter.formatToShortForm(power)));
            add(powerLabel);
            return this;
        }

        private boolean powerAvailable() {
            return power >= 0;
        }

        public void setData (int power){
            this.power = power;
        }
    }
    private static class StatsWidget extends Table {
        private ObjectMap<Stat, Float> stats;

        public Table construct () {
            background(Squircle.SQUIRCLE_35.getDrawable(Color.valueOf("#AE9E91"))).pad(20);

            add(constructStatsSegment()).growX();
            add(constructMenuButton()).size(100).space(20);
            return this;
        }
        public void setData(ObjectMap<Stat, Float> stats){
            this.stats = stats;
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

            for (Stat stat : Stat.values()) {
                String statText = "";

                if(stat.getDefaultType() == Stat.Type.ADDITIVE) {
                    statText = NumberFormatter.formatToShortForm((long)(float)stats.get(stat));
                }
                else if(stat.getDefaultType() == Stat.Type.MULTIPLICATIVE) {
                    statText = String.format("%.2f", stats.get(stat)) + "%";
                }
                statsContainer.add(createStat(stat.getTitle(), statText));
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
        private GearWidget gearWidget;
        private SecondaryGearWidget tacticalsWidget;


        public void setData(ObjectMap<GearType, GearSaveData> gears, IntMap<TacticalSaveData> tacticals){
            gearWidget = new GearWidget();
            gearWidget.setData(gears);

            tacticalsWidget = new SecondaryGearWidget();
            tacticalsWidget.setData(tacticals);
        }
        public Table construct(){
            add(gearWidget.construct()).expandX().left();
            add(tacticalsWidget.construct()).expandX();

            return this;
        }
    }

    private static class GearWidget extends Table{
        private static final int gearCount = 6;
        private ObjectMap<GearType, GearSaveData> gears;
        private SetIndicatorSegment indicator;

        public void setData(ObjectMap<GearType, GearSaveData> gears){
            this.gears = gears;

            indicator = new SetIndicatorSegment();
            indicator.setData();
        }

        public Table construct(){
            assert gearsAvailable() : "Invalid number of gears: expected " + gearCount + ", but got " + (gears == null ? "null" : gears.size);

            background(Squircle.SQUIRCLE_35.getDrawable(Color.valueOf("#CAC9C7"))).pad(15,30,15,30);

            add(indicator.construct()).growX().height(50);
            row();
            add(constructEquipmentSegment()).spaceTop(15);

            return this;
        }

        private Table constructEquipmentSegment(){
            final WidgetsContainer<Table> gearContainer = new WidgetsContainer<>(3);
            gearContainer.defaults().size(defaultRectSize)
                .space(20);
            for (ObjectMap.Entry<GearType, GearSaveData> gear : gears) {
                final BorderedTable item = constructGearCell(gear.value);
                gearContainer.add(item);
            }

            return gearContainer;
        }
        private static BorderedTable constructGearCell(GearSaveData gear) {
            GearsGameData gearsGameData = API.get(GameData.class).getGearsGameData();

            final Table icon = new Table();
            icon.background(gearsGameData.getGears().get(gear.getSkin()).getDrawable());

            final BorderedTable item = new BorderedTable(
                Squircle.SQUIRCLE_35.getDrawable(Color.valueOf("#B19985")),
                Squircle.SQUIRCLE_35_BORDER.getDrawable(Color.valueOf("#7F7268"))
            );

            item.pad(20).add(icon).grow();
            return item;
        }
        private boolean gearsAvailable() {
            return gears != null && gears.size == gearCount;
        }
    }
    private static class SetIndicatorSegment extends Table{
        public Table construct(){
            background(Squircle.SQUIRCLE_15.getDrawable(Color.valueOf("#A29890")));

            final Label setIndicatorText = Labels.make(GameFont.BOLD_20, Color.valueOf("#524B40"), "Nepolniy Nabor");
            add(setIndicatorText);

            return this;
        }
        public void setData(){}
    }

    private static class SecondaryGearWidget extends Table{
        private TacticalsWidget tacticalsWidget;
        private FlagWidget flagWidget;
        private PetWidget petWidget;

        public void setData(IntMap<TacticalSaveData> tacticals){
            tacticalsWidget = new TacticalsWidget();
            tacticalsWidget.setData(tacticals);

            flagWidget = new FlagWidget();
            flagWidget.setData();

            petWidget = new PetWidget();
            petWidget.setData();
        }
        public Table construct(){
            final Table tacticalsFlagContainer = new Table();
            tacticalsFlagContainer.defaults().space(25).size(defaultRectSize);
            tacticalsFlagContainer.add(tacticalsWidget.construct());
            tacticalsFlagContainer.row();
            tacticalsFlagContainer.add(flagWidget.construct());

            final Table segment = new Table();
            segment.add(tacticalsFlagContainer).spaceRight(25);
            segment.add(petWidget.construct()).growY().width(defaultRectSize);
            return segment;
        }

    }
    private static class FlagWidget extends BorderedTable{
        public void setData(){}
        public Table construct(){
            background(Squircle.SQUIRCLE_35.getDrawable(Color.valueOf("#C2B8AF")));
            setBorderDrawable(Squircle.SQUIRCLE_35_BORDER.getDrawable(Color.valueOf("#84786E")));

            return this;
        }
    }
    private static class PetWidget extends BorderedTable{
        public void setData(){}
        public Table construct(){
            background(Squircle.SQUIRCLE_35.getDrawable(Color.valueOf("#C2B8AF")));
            setBorderDrawable(Squircle.SQUIRCLE_35_BORDER.getDrawable(Color.valueOf("#84786E")));

            return this;
        }
    }
    private static class TacticalsWidget extends PressableTable {
        TacticalsWidget(){
            setOnClick(() -> {
                API.get(DialogManager.class).show(TestDialog.class);
            });
        }
        private static final int tacticalCount = 4;
        private IntMap<TacticalSaveData> tacticals;

        private WidgetsContainer<Table> tacticalsContainer;

        public void setData(IntMap<TacticalSaveData> tacticals){
            this.tacticals = tacticals;
        }
        public Table construct(){
            assert tacticalsAvailable() : "Invalid number of tacticals: expected " + tacticalCount + ", but got " + (tacticals == null ? "null" : tacticals.size);

            tacticalsContainer = new WidgetsContainer<>(tacticalCount);
            tacticalsContainer.setWidgetPerRow(2);
            tacticalsContainer.background(Squircle.SQUIRCLE_35.getDrawable(Color.valueOf("#C4B7AE"))).pad(10);
            tacticalsContainer.defaults().space(10).grow().uniform();

            for (IntMap.Entry<TacticalSaveData> tactical : tacticals) {
                Table item = constructTactical(tactical.value);
                tacticalsContainer.add(item);
            }

            add(tacticalsContainer);
            return this;
        }

        private Table constructTactical(TacticalSaveData tactical){
            TacticalsGameData tacticalsGameData = API.get(GameData.class).getTacticalsGameData();
            final Table tacticalIcon = new Table();
            tacticalIcon.setBackground(tacticalsGameData.getTacticals().get(tactical.getName()).getDrawable());

            final BorderedTable item = new BorderedTable(
                Squircle.SQUIRCLE_35.getDrawable(Color.valueOf("#B19985")),
                Squircle.SQUIRCLE_35_BORDER.getDrawable(Color.valueOf("#7F7268"))
            );
            item.add(tacticalIcon).growY();
            item.pad(10).setTouchable(Touchable.disabled);
            return item;
        }
        private boolean tacticalsAvailable(){
            return tacticals != null && tacticals.size==tacticalCount;
        }
    }
}
