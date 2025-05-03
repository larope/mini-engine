package com.bootcamp.demo.util.services;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.Disposable;
import com.bootcamp.demo.engine.Resources;
import com.bootcamp.demo.util.serviceLocator.Service;
import com.bootcamp.demo.util.serviceLocator.ServiceLocator;

import java.util.HashMap;
import java.util.Map;

public class ImageFactory implements Service, Disposable {
    public ImageFactory(){
        paths.put(CommonImages.SQUIRCLE_35, "basics/white-squircle-35");
        paths.put(CommonImages.GIFT, "ui/ui-chat-gift-button-icon");
        paths.put(CommonImages.SQUIRCLE_35_BORDER, "basics/white-squircle-border-35");
        paths.put(CommonImages.SQUIRCLE_35_TOP, "basics/basics/white-squircle-top-35");
    }

    public
    enum CommonImages{
        SQUIRCLE_35,
        SQUIRCLE_35_BORDER,
        SQUIRCLE_35_TOP,
        GIFT
    }

    private Map<CommonImages, String> paths = new HashMap<CommonImages, String>();

    public Image createImage(CommonImages type, Color color){
        final Image image = new Image();
        image.setDrawable(Resources.getDrawable(paths.get(type), color));
        return image;
    }

    public Image createImage(CommonImages type){
        return createImage(type, Color.WHITE);
    }

    public Drawable getDrawable(CommonImages type){
        return Resources.getDrawable(paths.get(type));
    }
    public Drawable getDrawable(CommonImages type, Color color){
        return Resources.getDrawable(paths.get(type), color);
    }
    @Override
    public void dispose() {
        unregister();
    }

    @Override
    public void register() {
        ServiceLocator.register(ImageFactory.class, this);
    }

    @Override
    public void unregister() {
        ServiceLocator.unregister(ImageFactory.class);
    }
}
