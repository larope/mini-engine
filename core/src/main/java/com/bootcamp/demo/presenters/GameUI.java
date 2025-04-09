package com.bootcamp.demo.presenters;

import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Cell;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.WidgetGroup;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.bootcamp.demo.engine.Squircle;
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
import sun.tools.jconsole.Tab;

public class GameUI extends ScreenAdapter implements Disposable, EventListener {

    @Getter
    private final Stage stage;
    @Getter
    private final Table rootUI;
    @Getter
    private final Cell<APage> mainPageCell;

    @Getter @Setter
    private boolean buttonPressed;

    private ImageFactory imageFactory;

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
    private final int size = 200;
    private final int defaultSpace = 30;
    private void playground () {
        imageFactory = ServiceLocator.get(ImageFactory.class);
        final Table playground = new Table();
        final Table main = new Table();
        main.background(Squircle.SQUIRCLE_35.getDrawable(Color.valueOf("#fefae0")))
            .pad(defaultSpace)
            .padLeft(300)
            .padRight(300)
            .defaults().spaceBottom(20).spaceTop(20).expandX().fillX();

        main.add(constructStatsSegment());
        main.row();
        main.add(constructAccessoriesSegment());
        main.row();
        main.add(constructButtonSegment());
        playground.add(main).expand().fillX().bottom();
        rootUI.add(playground).expand().fill();
    }

    private Table constructStatsSegment(){
        GridLayout stats = new GridLayout(3,3);
        stats.setDefaultCell(()-> imageFactory.createImage(ImageFactory.CommonImages.SQUIRCLE35, Color.valueOf("#d4a373")));
        stats.defaults().width(350).height(60).space(defaultSpace);
        stats.bind();

        Table menuButton = new Table();
        menuButton.setBackground(Squircle.SQUIRCLE_35.getDrawable(Color.valueOf("#d4a373")));

        Table statsSegment = new Table();
        statsSegment.background(Squircle.SQUIRCLE_35.getDrawable(Color.valueOf("#faedcd"))).pad(defaultSpace).defaults().space(defaultSpace);
        statsSegment.add(stats);
        statsSegment.add(menuButton).size(200);
        return statsSegment;
    }

    private Table constructAccessoriesSegment(){
        final GridLayout accessories = new GridLayout(3,2);

        accessories.setDefaultCell(()-> imageFactory.createImage(ImageFactory.CommonImages.SQUIRCLE35, Color.valueOf("#d4a373")));

        accessories.defaults().size(size).space(defaultSpace);
        accessories.bind();


        final Table secondaryAccessories = new Table();
        secondaryAccessories.defaults().space(defaultSpace);
        final Table secondaryLeft = new Table();
        secondaryLeft.add(imageFactory.createImage(ImageFactory.CommonImages.SQUIRCLE35, Color.valueOf("#d4a373"))).size(size).space(defaultSpace);
        secondaryLeft.row();
        secondaryLeft.add(imageFactory.createImage(ImageFactory.CommonImages.SQUIRCLE35, Color.valueOf("#d4a373"))).size(size).space(defaultSpace);


        final Table secondaryRight = new Table();
        secondaryRight.background(Squircle.SQUIRCLE_35.getDrawable(Color.valueOf("#d4a373")));

        secondaryAccessories.add(secondaryLeft);
        secondaryAccessories.add(secondaryRight).width(size).expandY().fillY();


        final Table accessoriesSegment = new Table();
        accessoriesSegment.background(Squircle.SQUIRCLE_35.getDrawable(Color.valueOf("#faedcd"))).pad(20);
        accessoriesSegment.defaults().space(80);
        accessoriesSegment.add(accessories);
        accessoriesSegment.add(secondaryAccessories).fillY();

        return accessoriesSegment;
    }

    private Table constructButtonSegment(){
        Table buttonSegment = new Table();
        buttonSegment.background(Squircle.SQUIRCLE_35.getDrawable(Color.valueOf("#faedcd"))).pad(50).defaults().width(400).height(200).space(defaultSpace);
        buttonSegment.add(imageFactory.createImage(ImageFactory.CommonImages.SQUIRCLE35, Color.valueOf("#d4a373")));
        buttonSegment.add(imageFactory.createImage(ImageFactory.CommonImages.SQUIRCLE35, Color.valueOf("#d4a373")));
        buttonSegment.add(imageFactory.createImage(ImageFactory.CommonImages.SQUIRCLE35, Color.valueOf("#d4a373")));
        return buttonSegment;
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
