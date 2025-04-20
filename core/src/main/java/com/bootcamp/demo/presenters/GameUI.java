package com.bootcamp.demo.presenters;

import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Cell;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.bootcamp.demo.engine.FontManager;
import com.bootcamp.demo.events.core.EventListener;
import com.bootcamp.demo.events.core.EventModule;
import com.bootcamp.demo.localization.GameFont;
import com.bootcamp.demo.managers.API;
import com.bootcamp.demo.pages.HuntingPage;
import com.bootcamp.demo.pages.core.APage;
import com.bootcamp.demo.pages.core.PageManager;
import com.bootcamp.demo.util.services.ImageFactory;
import lombok.Getter;
import lombok.Setter;

public class GameUI extends ScreenAdapter implements Disposable, EventListener {

    @Getter
    private final Stage stage;
    @Getter
    private final Table rootUI;
    @Getter
    private Cell<APage> mainPageCell;

    @Getter @Setter
    private boolean buttonPressed;

    private final FontManager fontManager;
    private final ImageFactory imageFactory;

    public GameUI (Viewport viewport) {
        fontManager = new FontManager();
        imageFactory = new ImageFactory();

        fontManager.register();
        fontManager.preloadFonts(GameFont.values());
        imageFactory.register();

        API.Instance().register(GameUI.class, this);
        API.get(EventModule.class).registerListener(this);

        rootUI = new Table();
        rootUI.setFillParent(true);

        // init stage
        stage = new Stage(viewport);
        stage.addActor(rootUI);

        // construct
        mainPageCell = rootUI.add().grow();

        API.get(PageManager.class).show(HuntingPage.class);
    }

    @Override
    public void render (float delta) {
        stage.act(delta);
        stage.draw();

        API.get(PageManager.class).show(HuntingPage.class);

    }

    @Override
    public void resize (int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void dispose () {
        stage.dispose();
        fontManager.dispose();
        imageFactory.dispose();
    }
}
