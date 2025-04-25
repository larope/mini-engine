package com.bootcamp.demo.pages;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.IntMap;
import com.badlogic.gdx.utils.ObjectMap;
import com.bootcamp.demo.data.game.*;
import com.bootcamp.demo.data.game.gear.GearGameData;
import com.bootcamp.demo.data.game.gear.GearType;
import com.bootcamp.demo.data.game.gear.GearsGameData;
import com.bootcamp.demo.data.game.tacticals.TacticalsGameData;
import com.bootcamp.demo.data.save.SaveData;
import com.bootcamp.demo.data.save.stats.Stat;
import com.bootcamp.demo.data.save.gears.GearSaveData;
import com.bootcamp.demo.data.save.tacticals.EquippedTacticalsSaveData;
import com.bootcamp.demo.data.save.tacticals.TacticalSaveData;
import com.bootcamp.demo.engine.Labels;
import com.bootcamp.demo.engine.Resources;
import com.bootcamp.demo.engine.Squircle;
import com.bootcamp.demo.engine.widgets.BorderedTable;
import com.bootcamp.demo.engine.widgets.OffsetButton;
import com.bootcamp.demo.engine.widgets.PressableTable;
import com.bootcamp.demo.engine.widgets.WidgetsContainer;
import com.bootcamp.demo.localization.GameFont;
import com.bootcamp.demo.mDialogs.ItemStatsDialog;
import com.bootcamp.demo.mDialogs.core.mADialog;
import com.bootcamp.demo.mDialogs.core.mDialogManager;
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
        IntMap<TacticalSaveData> tacticalsAsData = saveData.getInventorySaveData().getEquippedTacticalsSaveData().getTacticalsAsData();
        accessoriesWidget.setData(saveData.getGearsSaveData().getGears(), tacticalsAsData);

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
        private GearsWidget gearWidget;
        private SecondaryGearWidget tacticalsWidget;


        public void setData(ObjectMap<GearType, GearSaveData> gears, IntMap<TacticalSaveData> tacticals){
            gearWidget = new GearsWidget();
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


    private static class GearCell extends BorderedTable{
        GearSaveData data;
        GearsGameData gameData;

        final StarsSegment starsSegment = new StarsSegment();

        public GearCell(){
            gameData = API.get(GameData.class).getGearsGameData();
        }

        public Table construct(){
            pad(30);

            final Table icon = new Table();
            ObjectMap<String, GearGameData> gearSkins = gameData.getGears().get(data.getType());
            icon.background(gearSkins.get(data.getSkin()).getDrawable());

            add(icon).grow();
            addActor(constructStatsOverlay());
            return this;
        }

        public void setData(GearSaveData data){
            this.data = data;

            background(Squircle.SQUIRCLE_35.getDrawable(data.getRarity().getBackgroundColor()));
            setBorderDrawable(Squircle.SQUIRCLE_35_BORDER.getDrawable(data.getRarity().getBorderColor()));
        }

        private Table constructStatsOverlay(){
            final Table overlay = new Table();
            overlay.setFillParent(true);

            overlay.addActor(constructLevelOverlay());
            overlay.addActor(constructStarOverlay());

            return overlay;
        }

        private Table constructLevelOverlay(){
            Label levelLabel = Labels.make(GameFont.BOLD_22, Color.WHITE, "Lv. " + data.getLevel());

            Table levelOverlay = new Table();

            levelOverlay.add(levelLabel).expand().bottom().left();
            levelOverlay.setFillParent(true);
            levelOverlay.pad(20);

            return levelOverlay;
        }

        private Table constructStarOverlay(){
            starsSegment.defaults().space(5);
            starsSegment.setData(4);
            starsSegment.enableStars(data.getStarCount());

            Table starOverlay = new Table();

            starOverlay.setFillParent(true);
            starOverlay.add(starsSegment).expand().top().left();
            starOverlay.pad(20);

            return starOverlay;
        }
    }
    private static class TacticalsCell extends BorderedTable{
        TacticalSaveData data;
        TacticalsGameData gameData;

        final StarsSegment starsSegment = new StarsSegment();

        public TacticalsCell(){
            gameData = API.get(GameData.class).getTacticalsGameData();
        }

        public Table construct(){
            final Table tacticalIcon = new Table();
            tacticalIcon.setBackground(gameData.getTacticals().get(data.getName()).getDrawable());

            add(tacticalIcon).growY();
            pad(10).setTouchable(Touchable.disabled);
            return this;
        }

        public void setData(TacticalSaveData data){
            this.data = data;

            background(Squircle.SQUIRCLE_35.getDrawable(data.getRarity().getBackgroundColor()));
            setBorderDrawable(Squircle.SQUIRCLE_35_BORDER.getDrawable(data.getRarity().getBorderColor()));
        }
    }

    private static class StarsSegment extends Table{
        private int maxStarCount;
        private final Array<Table> stars = new Array<>();

        private static Table createStar() {
            Image star = new Image(Resources.getDrawable("ui/star"));
            Table statsWrapper = new Table();

            statsWrapper.add(star).size(40);
            return statsWrapper;
        }

        public void setData(int maxStarCount){
            this.maxStarCount = maxStarCount;
        }
        public void enableStars(int count){
            matchStarCount();
            disableAll();

            for (int i = 0; i < count; i++) {
                stars.get(i).setVisible(true);
            }
        }
        private void matchStarCount(){
            if(maxStarCount > stars.size){
                for (int i = stars.size; i < maxStarCount; i++) {
                    Table star = createStar();
                    add(star);
                    stars.add(star);
                }
            }
        }
        private void disableAll(){
            for (int i = 0; i < maxStarCount; i++) {
                stars.get(i).setVisible(false);
            }
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

    private static class GearsWidget extends Table{
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

            for (GearType type : GearType.values()) {
                GearSaveData gear = gears.get(type);
                final GearCell item = new GearCell();
                item.setData(gear);

                gearContainer.add(item.construct());
            }

            return gearContainer;
        }

        private boolean gearsAvailable() {
            return gears != null && gears.size == gearCount;
        }
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
            ItemStatsDialog dialog = new ItemStatsDialog();
            dialog.hide();
            API.get(mDialogManager.class).setDialog(dialog);

            setOnClick(() -> {
                API.get(mDialogManager.class).show();
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

            for (int i = 0; i < EquippedTacticalsSaveData.TACTICALS_COUNT; i++) {
                TacticalSaveData tactical = tacticals.get(i);
                if(tactical != null){
                    final TacticalsCell item = new TacticalsCell();
                    item.setData(tactical);
                    tacticalsContainer.add(item.construct());
                }
                else{
                    final Table emptyCell = new Table();
                    tacticalsContainer.add(emptyCell);
                }
            }
            add(tacticalsContainer);
            return this;
        }

        private boolean tacticalsAvailable(){
            return tacticals != null && tacticals.size==tacticalCount;
        }
    }
}
