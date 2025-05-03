package com.bootcamp.demo.mDialogs;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.bootcamp.demo.engine.Resources;
import com.bootcamp.demo.engine.Squircle;
import com.bootcamp.demo.engine.widgets.OffsetButton;
import com.bootcamp.demo.engine.widgets.PressableTable;
import com.bootcamp.demo.mDialogs.core.mADialog;

public class ItemStatsDialog extends mADialog {
    @Override
    protected void construct(){
        final Table background = new Table();
        background.background(Squircle.SQUIRCLE_50.getDrawable(Color.LIGHT_GRAY));
        content.add(background).size(1000, 1600);

        CloseButton closeButton = new CloseButton(OffsetButton.Style.RED_35);
        closeButton.setOffset(35);

        background.add(closeButton).size(100);
    }

    private static class CloseButton extends OffsetButton {
        public CloseButton(OffsetButton.Style style) {
            super(style);
        }
        @Override
        protected void buildInner(Table container){
            super.buildInner(container);

            Image closeIcon = new Image(Resources.getDrawable("ui/close", Color.WHITE));
            container.add(closeIcon).grow();
        }
    }
}
