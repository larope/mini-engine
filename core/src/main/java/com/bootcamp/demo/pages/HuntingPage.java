package com.bootcamp.demo.pages;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.*;
import com.bootcamp.demo.data.game.*;
import com.bootcamp.demo.data.game.gear.GearGameData;
import com.bootcamp.demo.data.game.gear.GearType;
import com.bootcamp.demo.data.game.gear.GearsGameData;
import com.bootcamp.demo.data.game.tacticals.TacticalGameData;
import com.bootcamp.demo.data.game.tacticals.TacticalsGameData;
import com.bootcamp.demo.data.save.SaveData;
import com.bootcamp.demo.data.save.stats.Stat;
import com.bootcamp.demo.data.save.gears.GearSaveData;
import com.bootcamp.demo.data.save.tacticals.EquippedTacticalsSaveData;
import com.bootcamp.demo.data.save.tacticals.TacticalSaveData;
import com.bootcamp.demo.dialogs.TacticalsDialog;
import com.bootcamp.demo.dialogs.core.DialogManager;
import com.bootcamp.demo.engine.Labels;
import com.bootcamp.demo.engine.Resources;
import com.bootcamp.demo.engine.Squircle;
import com.bootcamp.demo.engine.widgets.BorderedTable;
import com.bootcamp.demo.engine.widgets.OffsetButton;
import com.bootcamp.demo.engine.widgets.PressableTable;
import com.bootcamp.demo.engine.widgets.WidgetsContainer;
import com.bootcamp.demo.localization.GameFont;
import com.bootcamp.demo.dialogs.ItemStatsDialog;
import com.bootcamp.demo.managers.API;
import com.bootcamp.demo.managers.StatManager;
import com.bootcamp.demo.pages.core.APage;
import com.bootcamp.demo.util.NumberFormatter;
import jdk.vm.ci.amd64.AMD64;
import lombok.Getter;
import lombok.Setter;

public class HuntingPage extends APage {
    static final int defaultRectSize = 225;

    private StatsWidget statsWidget;
    private AccessoriesWidget accessoriesWidget;
    private ButtonSegment buttonSegment;
    private PowerWidget powerWidget;

    @Override
    protected void constructContent(Table content) {
        powerWidget = new PowerWidget();

        final Table topUI = new Table();
        final Table mainUI = constructMainUI();

        // assemble
        content.add(topUI).grow();
        content.row();
        content.add(powerWidget).expandX().height(100).width(600);
        content.row();
        content.add(mainUI).growX();

        setData();
    }

    private void setData(){
        StatManager statManager = API.get(StatManager.class);
        SaveData saveData = API.get(SaveData.class);

        powerWidget.setData(statManager.getPower());

        ObjectMap<String, TacticalSaveData> tacticals = saveData.getTacticalsSaveSata().getTacticals();
        Array<TacticalSaveData> onlyTacticals = tacticals.values().toArray();
        IntMap<TacticalSaveData> tacticalsAsIntmap = new IntMap<>();

        for (int i = 0; i < onlyTacticals.size; i++) {
            tacticalsAsIntmap.put(i, onlyTacticals.get(i));
        }

        ObjectMap<GearType, GearSaveData> gears = saveData.getGearsSaveData().getGears();
        accessoriesWidget.setData(gears, tacticalsAsIntmap);

        buttonSegment.setData();
        statsWidget.setData(statManager.getAllStatsCombined());
    }

    private Table constructMainUI () {
        accessoriesWidget = new AccessoriesWidget();
        buttonSegment = new ButtonSegment();
        statsWidget = new StatsWidget();

        final Table mainUI = new Table();
        mainUI.background(Squircle.SQUIRCLE_35_TOP.getDrawable(Color.valueOf("#fbeae0")));
        mainUI.pad(30);
        mainUI.defaults().growX();

        mainUI.add(statsWidget);
        mainUI.row();
        mainUI.add(accessoriesWidget).space(30);
        mainUI.row();
        mainUI.add(buttonSegment);

        return mainUI;
    }

    private static class PowerWidget extends BorderedTable {
        private final Label powerLabel;

        public PowerWidget(){
            setTouchable(Touchable.disabled);
            background(Squircle.SQUIRCLE_35_TOP.getDrawable(Color.valueOf("#AE9E91")));
            setBorderDrawable(Squircle.SQUIRCLE_35_BORDER_TOP.getDrawable(Color.valueOf("#fbeae0")));

            powerLabel = Labels.make(GameFont.BOLD_32, Color.WHITE, String.valueOf(NumberFormatter.formatToShortForm(0)));
            add(powerLabel);
        }

        public void setData (int power){
            powerLabel.setText(NumberFormatter.formatToShortForm(power));
        }
    }
    private static class StatContainer extends Table{
        private final Label name;
        private final Label value;
        public StatContainer(){
            name = Labels.make(GameFont.BOLD_20, Color.valueOf("#524B40"), "");
            value = Labels.make(GameFont.BOLD_20, Color.valueOf("#524B40"), "");
            value.setAlignment(Align.right);

            add(name).growX().left();
            add(value).growX().right();

            setEmpty();
        }

        public void setData(String name, String value){
            if(name == null || value == null){
                setEmpty();
                return;
            }

            this.name.setText(name);
            this.value.setText(value);
        }

        public void setEmpty(){
            this.name.setText("");
            this.value.setText("");
        }
    }
    private static class StatsWidget extends Table {
        private final WidgetsContainer<StatContainer> statsContainer;

        public StatsWidget(){
            background(Squircle.SQUIRCLE_35.getDrawable(Color.valueOf("#AE9E91"))).pad(20);

            statsContainer = new WidgetsContainer<>(3);
            statsContainer.pad(0, 20, 0, 20);
            statsContainer.defaults().growX().space(10, 40, 10, 40);


            for (Stat stat : Stat.values()) {
                StatContainer container = new StatContainer();
                statsContainer.add(container);
            }

            add(statsContainer).growX();

            Table menuButton = constructMenuButton();
            add(menuButton).size(100).space(20);
            setEmpty();
        }

        private Table constructMenuButton() {
            return new BorderedTable(
                Squircle.SQUIRCLE_35.getDrawable(Color.valueOf("#F1E6DC")),
                Squircle.SQUIRCLE_35_BORDER.getDrawable(Color.valueOf("#847467"))
            );
        }

        private void setData(ObjectMap<Stat, Float> stats){
            int i = 0;
            for (Stat stat : Stat.values()) {
                String statText = getText(stat, stats.get(stat));
                statsContainer.getWidgets().get(i).setData(stat.getTitle(), statText);
                i++;
            }
        }
        public void setEmpty(){
            int i = 0;
            for (Stat stat : Stat.values()) {
                String statText = getText(stat, 0);
                statsContainer.getWidgets().get(i).setData(stat.getTitle(), statText);
                i++;
            }
        }

        private String getText(Stat stat, float value){
            String text = "";

            if(stat.getDefaultType() == Stat.Type.ADDITIVE) {
                text = NumberFormatter.formatToShortForm((long)value);
            }
            else if(stat.getDefaultType() == Stat.Type.MULTIPLICATIVE) {
                text = String.format("%.2f", value) + "%";
            }

            return text;
        }
    }
    private static class ButtonSegment extends Table {
        public ButtonSegment(){
            final OffsetButton upgradeButton = new OffsetButton(OffsetButton.Style.YELLOW_35);
            final HuntingButton huntingButton = new HuntingButton();
            final OffsetButton autoHuntingButton = new OffsetButton(OffsetButton.Style.GRAY_35);

            upgradeButton.setOffset(35);
            huntingButton.setOffset(35);
            autoHuntingButton.setOffset(35);

            defaults().growX().space(30).height(defaultRectSize);
            add(upgradeButton);
            add(huntingButton);
            add(autoHuntingButton);
        }
        public void setData (){}
    }
    private static class AccessoriesWidget extends Table {
        private final GearsWidget gearWidget;
        private final SecondaryGearWidget tacticalsWidget;

        public AccessoriesWidget(){
            gearWidget = new GearsWidget();
            tacticalsWidget = new SecondaryGearWidget();

            add(gearWidget).expandX().left();
            add(tacticalsWidget).expandX();
        }

        public void setData(ObjectMap<GearType, GearSaveData> gears, IntMap<TacticalSaveData> tacticals){
            gearWidget.setData(gears);
            tacticalsWidget.setData(tacticals);
        }
    }


    private static class GearCell extends BorderedTable{
        private GearSaveData data;

        private final Label levelLabel;
        private final Image icon;
        private final StarsSegment starsSegment;

        @Setter @Getter
        private GearType type;

        public GearCell(){
            levelLabel = Labels.make(GameFont.BOLD_22, Color.WHITE);

            starsSegment = new StarsSegment();

            icon = new Image();
            pad(30);

            add(icon).grow();
            addActor(constructStatsOverlay());

            setOnClick(() -> {
                if(data == null){
                    return;
                }
                DialogManager dialogManager = API.get(DialogManager.class);
                ItemStatsDialog dialog = dialogManager.getDialog(ItemStatsDialog.class);
                dialog.setData(data);

                dialogManager.show(ItemStatsDialog.class);
            });
            setEmpty();
        }

        public void setData(GearSaveData data){
            this.data = data;
            if(data == null){
                setEmpty();
                return;
            }

            starsSegment.enableStars(data.getStarCount());

            background(Squircle.SQUIRCLE_35.getDrawable(data.getRarity().getBackgroundColor()));
            setBorderDrawable(Squircle.SQUIRCLE_35_BORDER.getDrawable(data.getRarity().getBorderColor()));

            final GearsGameData gameData = API.get(GameData.class).getGearsGameData();
            ObjectMap<String, GearGameData> gearSkins = gameData.getGears().get(data.getType());
            icon.setDrawable(gearSkins.get(data.getSkin()).getDrawable());
        }

        @Override
        public void setEmpty(){
            starsSegment.enableStars(1);
            background(Squircle.SQUIRCLE_35.getDrawable(Rarity.RUSTED.getBackgroundColor()));
            setBorderDrawable(Squircle.SQUIRCLE_35_BORDER.getDrawable(Rarity.RUSTED.getBorderColor()));
        }

        private Table constructStatsOverlay(){
            final Table overlay = new Table();
            overlay.setFillParent(true);

            overlay.addActor(constructLevelOverlay());
            overlay.addActor(constructStarOverlay());

            return overlay;
        }

        private Table constructLevelOverlay(){
            final Table levelOverlay = new Table();

            levelOverlay.add(levelLabel).expand().bottom().left();
            levelOverlay.setFillParent(true);
            levelOverlay.pad(20);

            return levelOverlay;
        }

        private Table constructStarOverlay(){
            starsSegment.defaults().space(5);

            final Table starOverlay = new Table();

            starOverlay.setFillParent(true);
            starOverlay.add(starsSegment).expand().top().left();
            starOverlay.pad(20);

            return starOverlay;
        }
    }
    private static class TacticalContainer extends BorderedTable {
        private final Image tacticalIcon;

        public TacticalContainer() {
            tacticalIcon = new Image();
            tacticalIcon.setScaling(Scaling.fit);

            add(tacticalIcon).growY();
            pad(10).setTouchable(Touchable.disabled);

            setEmpty();
        }

        public void setData (TacticalSaveData data) {
            if (data == null) {
                setEmpty();
                return;
            }

            final TacticalsGameData tacticalsGameData = API.get(GameData.class).getTacticalsGameData();
            final TacticalGameData tacticalGameData = tacticalsGameData.getTacticals().get(data.getName());
            tacticalIcon.setDrawable(tacticalGameData.getDrawable());
            background(Squircle.SQUIRCLE_35.getDrawable(data.getRarity().getBackgroundColor()));
            setBorderDrawable(Squircle.SQUIRCLE_35_BORDER.getDrawable(data.getRarity().getBorderColor()));
        }

        @Override
        public void setEmpty () {
            super.setEmpty();
            tacticalIcon.setDrawable(null);
        }
    }

    private static class StarsSegment extends Table{
        private final Array<Table> stars;
        public StarsSegment(){
            stars = new Array<>();
        }

        public void enableStars(int count){
            matchStarCount(count);
            disableAll();

            for (int i = 0; i < count; i++) {
                stars.get(i).setVisible(true);
            }
        }
        private void matchStarCount(int count){
            for (int i = stars.size; i < count; i++) {
                final Table star = addStar();
                add(star);
                stars.add(star);
            }
        }
        private Table addStar() {
            final Image star = new Image(Resources.getDrawable("ui/star"));
            final Table starWrapper = new Table();
            starWrapper.add(star).size(40);
            return starWrapper;
        }
        private void disableAll(){
            for (Table star : stars) {
                star.setVisible(false);
            }
        }
    }
    private static class SetIndicatorSegment extends Table{
        private final Label setIndicatorText;

        public SetIndicatorSegment(){
            background(Squircle.SQUIRCLE_15.getDrawable(Color.valueOf("#A29890")));

            setIndicatorText = Labels.make(GameFont.BOLD_20, Color.valueOf("#524B40"));
            add(setIndicatorText);
        }
        public void setData(boolean setIsFull){
            if(setIsFull){
                setIndicatorText.setText("Polniy nabor");
            }
            else{
               setIndicatorText.setText("Nepolnit nabor");
            }
        }
    }

    private static class GearsWidget extends Table{
        private final SetIndicatorSegment indicator;
        private final WidgetsContainer<GearCell> gearContainer;

        public GearsWidget(){
            background(Squircle.SQUIRCLE_35.getDrawable(Color.valueOf("#CAC9C7"))).pad(15,30,15,30);
            gearContainer = new WidgetsContainer<>(3);
            indicator = new SetIndicatorSegment();

            add(indicator).growX().height(50);
            row();
            add(constructEquipmentSegment()).spaceTop(15);
        }

        public void setData(ObjectMap<GearType, GearSaveData> gears){
            if(gears == null){
                setEmpty();
                return;
            }

            for (GearCell widget : gearContainer.getWidgets()) {
                widget.setData(gears.get(widget.getType()));
            }

            indicator.setData(false);
        }

        private void setEmpty() {
            indicator.setData(false);
        }

        private Table constructEquipmentSegment(){
            gearContainer.defaults().size(defaultRectSize)
                .space(20);

            for (GearType type : GearType.values()) {
                final GearCell item = new GearCell();
                item.setData(null);
                item.setType(type);

                gearContainer.add(item);
            }

            return gearContainer;
        }
    }
    private static class SecondaryGearWidget extends Table{
        private TacticalsWidget tacticalsWidget;
        private FlagWidget flagWidget;
        private PetWidget petWidget;

        public SecondaryGearWidget(){
            tacticalsWidget = new TacticalsWidget();
            flagWidget = new FlagWidget();
            petWidget = new PetWidget();

            final Table tacticalsFlagContainer = new Table();
            tacticalsFlagContainer.defaults().space(25).size(defaultRectSize);
            tacticalsFlagContainer.add(tacticalsWidget);
            tacticalsFlagContainer.row();
            tacticalsFlagContainer.add(flagWidget);

            add(tacticalsFlagContainer).spaceRight(25);
            add(petWidget).growY().width(defaultRectSize);
        }

        public void setData(IntMap<TacticalSaveData> tacticals){
            tacticalsWidget.setData(tacticals);
            flagWidget.setData();
            petWidget.setData();
        }
    }
    private static class FlagWidget extends BorderedTable{
        public FlagWidget(){
            background(Squircle.SQUIRCLE_35.getDrawable(Color.valueOf("#C2B8AF")));
            setBorderDrawable(Squircle.SQUIRCLE_35_BORDER.getDrawable(Color.valueOf("#84786E")));
        }

        public void setData(){}
    }
    private static class PetWidget extends BorderedTable{
        public PetWidget(){
            background(Squircle.SQUIRCLE_35.getDrawable(Color.valueOf("#C2B8AF")));
            setBorderDrawable(Squircle.SQUIRCLE_35_BORDER.getDrawable(Color.valueOf("#84786E")));
        }
        public void setData(){}
    }
    private static class TacticalsWidget extends PressableTable {
        private final WidgetsContainer<TacticalContainer> tacticalsContainer;

        public TacticalsWidget() {
            tacticalsContainer = new WidgetsContainer<>((int) Math.sqrt(EquippedTacticalsSaveData.TACTICALS_COUNT));
            tacticalsContainer.setWidgetPerRow(2);
            tacticalsContainer.background(Squircle.SQUIRCLE_35.getDrawable(Color.valueOf("#C4B7AE"))).pad(10);
            tacticalsContainer.defaults().space(10).grow().uniform();
            for (int i = 0; i < EquippedTacticalsSaveData.TACTICALS_COUNT; i++) {
                final TacticalContainer item = new TacticalContainer();
                tacticalsContainer.add(item);
            }

            add(tacticalsContainer);

            setOnClick(() -> {
                final DialogManager dialogManager = API.get(DialogManager.class);
                dialogManager.getDialog(TacticalsDialog.class).setData();
                dialogManager.show(TacticalsDialog.class);
            });
        }

        public void setData (IntMap<TacticalSaveData> tacticals) {
            final Array<TacticalContainer> widgets = tacticalsContainer.getWidgets();

            for (int i = 0; i < widgets.size; i++) {
                final TacticalContainer widget = widgets.get(i);
                widget.setData(tacticals.get(i));
            }
        }
    }

    private static class HuntingButton extends OffsetButton {
        private final Image shovelIcon;

        public HuntingButton () {
            shovelIcon = new Image(Resources.getDrawable("ui/ad-ticket-icon"), Scaling.fit);
            build(OffsetButton.Style.GREEN_35);

            setOnClick(() -> {
//                final EventModule eventModule = API.get(EventModule.class);
//                final LootedEvent event = eventModule.obtainEvent(LootedEvent.class);
//                eventModule.fireEvent(event);
            });
        }

        @Override
        protected void buildInner(Table container) {
            super.buildInner(container);
            container.add(shovelIcon).size(80);
        }
    }
}
