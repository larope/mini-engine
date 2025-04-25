package com.bootcamp.demo.mDialogs.core;

import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.Actor;

public interface IDialog {
    Actor getActor();
    void show();
    void hide();
}
