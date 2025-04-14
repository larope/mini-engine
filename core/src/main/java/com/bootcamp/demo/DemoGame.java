package com.bootcamp.demo;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.bootcamp.demo.engine.FontManager;
import com.bootcamp.demo.events.GameStartedEvent;
import com.bootcamp.demo.localization.GameFont;
import com.bootcamp.demo.managers.API;
import com.bootcamp.demo.events.core.EventModule;
import com.bootcamp.demo.util.services.ImageFactory;

public class DemoGame extends Game {
    private ImageFactory imageFactory;
    private FontManager fontManager;
    @Override
    public void create () {
        imageFactory = new ImageFactory();
        fontManager = new FontManager();
        imageFactory.register();
        fontManager.register();
        fontManager.preloadFonts(GameFont.values());

        Gdx.input.setInputProcessor(new InputMultiplexer());

        setScreen(new GameScreen());
        API.get(EventModule.class).fireEvent(GameStartedEvent.class);
    }

    @Override
    public void dispose () {
        super.dispose();

        imageFactory.dispose();
        fontManager.dispose();

        API.Instance().dispose();
        Gdx.app.exit();
    }
}
