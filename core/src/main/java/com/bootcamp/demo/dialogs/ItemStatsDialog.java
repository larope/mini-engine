package com.bootcamp.demo.dialogs;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.ObjectMap;
import com.bootcamp.demo.data.save.gears.GearSaveData;
import com.bootcamp.demo.data.save.gears.GearSkinSaveData;
import com.bootcamp.demo.data.save.stats.Stat;
import com.bootcamp.demo.data.save.stats.StatEntry;
import com.bootcamp.demo.dialogs.core.ADialog;
import com.bootcamp.demo.engine.Labels;
import com.bootcamp.demo.engine.Squircle;
import com.bootcamp.demo.engine.widgets.WidgetsContainer;
import com.bootcamp.demo.localization.GameFont;
import com.bootcamp.demo.pages.HuntingPage;
import com.bootcamp.demo.util.NumberFormatter;
import lombok.Getter;
import lombok.Setter;
import sun.tools.jconsole.Tab;

public class ItemStatsDialog extends ADialog {
    private StatsWidget statsWidget;
    private SkinStatsWidget skinStatsWidget;

    @Override
    protected Drawable getDialogBackground () {
        return Squircle.SQUIRCLE_35.getDrawable(Color.valueOf("#fbeae0"));
    }

    @Override
    protected void constructContent(Table content) {
        final Table width = new Table();
        content.add(width).width(1000).height(1).expand().top();
        content.row();

        titleLabel.setColor(Color.BLACK);
        getDialogBackground();

        statsWidget = new StatsWidget();
        skinStatsWidget = new SkinStatsWidget();
        content.add(statsWidget).grow();
        row();
        content.add(skinStatsWidget).grow();
    }
    @Override
    protected void constructDialog(Table dialog){
        super.constructDialog(dialog);
        dialog.pad(50);
        content.pad(50);
        content.background(Squircle.SQUIRCLE_35.getDrawable(Color.valueOf("#C2B8AF")));
    }
    @Override
    protected Table constructContentWrapper(){
        final Table contentWrapper = new Table();
        titleSegmentCell = contentWrapper.add(titleSegment).growX();
        contentWrapper.row();
        contentWrapper.add(content).grow().spaceTop(50);
        return contentWrapper;
    }

    public void setData(GearSaveData data){
        setTitle(data.getSkin());
        statsWidget.setData(data);
        skinStatsWidget.setData(data.getSkinData());
    }

    private static class StatsWidget extends Table{
        private final HuntingPage.GearContainer gearContainer;
        private final Label power;
        private final Label rarity;
        private final Label type;


        public StatsWidget(){
            power = Labels.make(GameFont.BOLD_32, Color.WHITE);
            rarity = Labels.make(GameFont.BOLD_32);
            type = Labels.make(GameFont.BOLD_32, Color.WHITE);

            gearContainer = new HuntingPage.GearContainer();
            gearContainer.getStarsSegment().setStarSize(60);
            gearContainer.setTouchable(Touchable.disabled);
            add(gearContainer).size(300).expand().left();

            Table statsSegment = constructStatsSegment();
            add(statsSegment).grow();
        }

        private Table constructStatsSegment(){
            Label powerLabel = Labels.make(GameFont.BOLD_32, Color.WHITE, "Power:");
            Label rarityLabel = Labels.make(GameFont.BOLD_32, Color.WHITE, "Rarity:");
            Label typeLabel = Labels.make(GameFont.BOLD_32, Color.WHITE, "Type:");

            Table powerWrapper = new Table();
            powerWrapper.add(powerLabel).expand().left();
            powerWrapper.add(power).expand().right();

            Table rarityWrapper = new Table();
            rarityWrapper.add(rarityLabel).expand().left();
            rarityWrapper.add(rarity).expand().right();

            Table typeWrapper = new Table();
            typeWrapper.add(typeLabel).expand().left();
            typeWrapper.add(type).expand().right();

            final WidgetsContainer<Table> segment = new WidgetsContainer<>(1);
            segment.defaults().grow();
            segment.add(powerWrapper);
            segment.add(rarityWrapper);
            segment.add(typeWrapper);
            return segment;
        }


        public void setData(GearSaveData data){
            if(data == null){
                setEmpty();
                return;
            }
            gearContainer.setData(data);
            power.setText(NumberFormatter.formatToShortForm(data.getStats().getPower()));
            rarity.setText(data.getRarity().name().toUpperCase());
            rarity.setColor(data.getRarity().getBackgroundColor());
            type.setText(data.getType().name());

            ObjectMap<Stat, StatEntry> stats = data.getStats().getValues();

        }

        private void setEmpty(){

        }
    }
    private static class SkinStatsWidget extends Table{
        public SkinStatsWidget(){

        }

        public void setData(GearSkinSaveData data){
            if(data == null){
                setEmpty();
                return;
            }
        }

        private void setEmpty(){

        }
    }
}
