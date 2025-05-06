package com.bootcamp.demo.dialogs;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.ObjectMap;
import com.badlogic.gdx.utils.Scaling;
import com.bootcamp.demo.data.save.SaveData;
import com.bootcamp.demo.data.save.gears.GearSaveData;
import com.bootcamp.demo.data.save.stats.Stat;
import com.bootcamp.demo.data.save.stats.StatEntry;
import com.bootcamp.demo.dialogs.core.ADialog;
import com.bootcamp.demo.dialogs.core.DialogManager;
import com.bootcamp.demo.engine.Labels;
import com.bootcamp.demo.engine.Resources;
import com.bootcamp.demo.engine.Squircle;
import com.bootcamp.demo.engine.widgets.OffsetButton;
import com.bootcamp.demo.engine.widgets.WidgetsContainer;
import com.bootcamp.demo.events.core.EventHandler;
import com.bootcamp.demo.events.core.EventModule;
import com.bootcamp.demo.events.example.LootedEvent;
import com.bootcamp.demo.localization.GameFont;
import com.bootcamp.demo.managers.API;
import com.bootcamp.demo.pages.HuntingPage;
import com.bootcamp.demo.util.NumberFormatter;

import javax.xml.stream.EventFilter;

public class LootItemDialog extends ItemStatsDialog {
    private EquipButton equipButton;
    private DropButton dropButton;

    @Override
    protected void constructContent(Table content) {
        super.constructContent(content);
        content.row();
        Table buttonSegment = constructButtonSegment();
        content.add(buttonSegment).spaceTop(120);
    }

    @Override
    public void setData(GearSaveData data){
        super.setData(data);
        equipButton.setData(data);

        Label power = statsWidget.getPower();

        GearSaveData current = API.get(SaveData.class).getGearsSaveData().getGears().put(data.getType(), data);
        if(current.getStats().getPower() > data.getStats().getPower()){
            power.setColor(Color.RED);
        }
        else if(current.getStats().getPower() <= data.getStats().getPower()){
            power.setColor(Color.GREEN);
        }
    }

    private Table constructButtonSegment(){
        equipButton = new EquipButton();
        equipButton.setOffset(35);

        dropButton = new DropButton();
        dropButton.setOffset(35);

        final Table buttonWrapper = new Table();
        buttonWrapper.add(equipButton).height(200).width(400).spaceRight(100);
        buttonWrapper.add(dropButton).height(200).width(400);

        return buttonWrapper;
    }

    private static class EquipButton extends OffsetButton{
        private GearSaveData data;

        public EquipButton () {
            build(OffsetButton.Style.GREEN_35);
            setOnClick(() -> {
                API.get(SaveData.class).getGearsSaveData().getGears().put(data.getType(), data);
                API.get(DialogManager.class).hide(LootItemDialog.class);
                API.get(EventModule.class).fireEvent(LootedEvent.class);
            });
        }

        public void setData(GearSaveData data){
            this.data = data;
        }

        @Override
        protected void buildInner(Table container) {
            super.buildInner(container);
            final Label lootText = Labels.make(GameFont.BOLD_32, Color.WHITE, "Equip");
            container.add(lootText).spaceRight(10);
        }
    }
    private static class DropButton extends OffsetButton{
        public DropButton () {
            build(Style.RED_35);
            setOnClick(() -> {
                API.get(DialogManager.class).hide(LootItemDialog.class);
            });
        }

        @Override
        protected void buildInner(Table container) {
            super.buildInner(container);
            final Label lootText = Labels.make(GameFont.BOLD_32, Color.WHITE, "Drop");
            container.add(lootText).spaceRight(10);
        }
    }
}
