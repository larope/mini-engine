package com.bootcamp.demo.mDialogs.core;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.bootcamp.demo.engine.Resources;


import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;

import java.util.Objects;

public class mADialog implements IDialog {
    private final Table outerPart;
    protected final Table content;

    public mADialog() {
        content = new Table();
        content.setName("content");
        construct();

        outerPart = new Table();
        outerPart.setBackground(Resources.getDrawable("basics/white-pixel", new Color(0, 0, 0, 0.65f)));
        outerPart.setTouchable(Touchable.enabled);
        outerPart.add(content).grow();
        outerPart.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Actor hit = outerPart.hit(x, y, false);
                Gdx.app.log("LOG:", hit.getName());
                if (hit != null && !Objects.equals(hit.getName(), "content")) {

                } else {
                    hide();
                }
            }
        });
    }

    protected void construct(){

    }

    @Override
    public Actor getActor() {
        return outerPart;
    }

    @Override
    public void show() {
        outerPart.setVisible(true);
    }

    @Override
    public void hide() {
        outerPart.setVisible(false);
    }
}
