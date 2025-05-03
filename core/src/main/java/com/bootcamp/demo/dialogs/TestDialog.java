package com.bootcamp.demo.dialogs;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.bootcamp.demo.dialogs.core.ADialog;
import com.bootcamp.demo.engine.Squircle;

public class TestDialog extends ADialog {

    @Override
    protected void constructContent (Table content) {
        Table inner = new Table();
        inner.background(Squircle.SQUIRCLE_35.getDrawable(Color.BLUE));
        content.add(inner).size(700);
    }
}
