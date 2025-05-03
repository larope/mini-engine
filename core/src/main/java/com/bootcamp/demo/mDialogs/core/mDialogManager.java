package com.bootcamp.demo.mDialogs.core;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import lombok.Setter;

public class mDialogManager {
    private IDialog currentDialog;
    @Setter
    private final Stage stage;
    private final Table rootUI;

    public mDialogManager(Stage stage) {
        this.stage = stage;
        this.rootUI = new Table();
        rootUI.setFillParent(true);
        stage.addActor(rootUI);
    }

    public void setDialog(final IDialog dialog) {
        if (currentDialog != null) {
            rootUI.removeActor(currentDialog.getActor());
        }
        currentDialog = dialog;
        rootUI.add(currentDialog.getActor()).grow().center();
    }

    public void show() {
        if (currentDialog != null) {
            currentDialog.show();
        }
    }

    public void hide() {
        if (currentDialog != null) {
            currentDialog.hide();
        }
    }
}

