package com.bootcamp.demo.pages;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Scaling;
import com.bootcamp.demo.data.game.GameData;
import com.bootcamp.demo.data.game.tacticals.TacticalsGameData;
import com.bootcamp.demo.data.save.SaveData;
import com.bootcamp.demo.data.save.tacticals.TacticalSaveData;
import com.bootcamp.demo.data.save.tacticals.TacticalsSaveData;
import com.bootcamp.demo.engine.Squircle;
import com.bootcamp.demo.engine.widgets.WidgetsContainer;
import com.bootcamp.demo.managers.API;
import com.bootcamp.demo.pages.core.APage;

public class TestPage extends APage {

    private TacticalsContainer tacticalsContainer;

    @Override
    protected void constructContent (Table content) {
        tacticalsContainer = new TacticalsContainer();
        content.add(tacticalsContainer);
    }

    public static class TacticalsContainer extends WidgetsContainer<TacticalContainer> {
        public TacticalsContainer () {
            super(2);
            setBackground(Squircle.SQUIRCLE_35.getDrawable(Color.valueOf("#c8c0b9")));
            pad(20).defaults().uniform().space(20).grow();

            for (int i = 0; i < 4; i++) {
                final TacticalContainer widget = new TacticalContainer();
                add(widget);
            }
        }

        public void setData (TacticalsSaveData tacticalsSaveData) {
            final Array<TacticalContainer> widgets = getWidgets();

            for (int i = 0; i < widgets.size; i++) {
                final TacticalContainer widget = widgets.get(i);
                final TacticalSaveData tacticalSaveData = tacticalsSaveData.getTacticals().get(i);
                widget.setData(tacticalSaveData);
            }
        }
    }

    public static class TacticalContainer extends Table {

        private final Image iconImage;

        public TacticalContainer () {
            setBackground(Squircle.SQUIRCLE_35.getDrawable(Color.valueOf("#a9a29c")));

            iconImage = new Image();
            iconImage.setScaling(Scaling.fit);
            add(iconImage).grow().pad(10);
        }

        public void setData (TacticalSaveData tacticalSaveData) {
            if (tacticalSaveData == null) {
                setEmpty();
                return;
            }

            final TacticalsGameData tacticalsGameData = API.get(GameData.class).getTacticalsGameData();

        }

        private void setEmpty () {
            iconImage.setDrawable(null);
        }
    }

    @Override
    public void show (Runnable onComplete) {
        super.show(onComplete);
        tacticalsContainer.setData(API.get(SaveData.class).getTacticalsSaveData());
    }
}
