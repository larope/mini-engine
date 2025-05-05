package com.bootcamp.demo.dialogs;

import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.bootcamp.demo.dialogs.core.ADialog;

public class TesticlesDialog extends ADialog {
    @Override
    protected void constructContent(Table content) {
        content.add().size(800, 600);
    }

    public void setData () {

    }
}
