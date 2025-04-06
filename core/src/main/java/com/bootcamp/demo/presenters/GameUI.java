package com.bootcamp.demo.presenters;

import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Cell;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.bootcamp.demo.gridLayout.DefaultCellFactory;
import com.bootcamp.demo.gridLayout.GridLayout;
import com.bootcamp.demo.engine.Resources;
import com.bootcamp.demo.events.core.EventListener;
import com.bootcamp.demo.events.core.EventModule;
import com.bootcamp.demo.managers.API;
import com.bootcamp.demo.pages.core.APage;
import com.bootcamp.demo.util.serviceLocator.ServiceLocator;
import com.bootcamp.demo.util.services.ImageFactory;
import lombok.Getter;
import lombok.Setter;

public class GameUI extends ScreenAdapter implements Disposable, EventListener {

    @Getter
    private final Stage stage;
    @Getter
    private final Table rootUI;
    @Getter
    private final Cell<APage> mainPageCell;

    @Getter @Setter
    private boolean buttonPressed;


    public GameUI (Viewport viewport) {
        API.Instance().register(GameUI.class, this);
        API.get(EventModule.class).registerListener(this);

        rootUI = new Table();
        rootUI.setFillParent(true);

        // init stage
        stage = new Stage(viewport);
        stage.addActor(rootUI);

        // construct
        mainPageCell = rootUI.add();

        playground();
    }

    private void playground () {
        final Table playground = new Table();

        GridLayout grid = new GridLayout(2, 5);
        grid.background(Resources.getDrawable("basics/white-squircle-35", Color.WHITE));
        grid.defaults().size(300);
        grid.defaults().space(20);
        grid.pad(20);

        grid.setDefaultCell(() -> ServiceLocator
            .get(ImageFactory.class)
            .createImage(ImageFactory.CommonImages.SQUIRCLE35, Color.LIGHT_GRAY)
        );

        grid.setCell(1,4, ServiceLocator
            .get(ImageFactory.class)
            .createImage(ImageFactory.CommonImages.GIFT)
        );

        grid.setCell(0,0, ServiceLocator
            .get(ImageFactory.class)
            .createImage(ImageFactory.CommonImages.GIFT)
        );

        grid.setCell(1,2, ServiceLocator
            .get(ImageFactory.class)
            .createImage(ImageFactory.CommonImages.GIFT)
        );

        grid.bind();

        playground.add(grid);

        rootUI.add(playground);
    }

    @Override
    public void render (float delta) {
        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize (int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void dispose () {
        stage.dispose();
    }
}
