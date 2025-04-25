package com.bootcamp.demo.data.save.inventory;

import com.badlogic.gdx.utils.ObjectMap;
import com.bootcamp.demo.data.game.gear.GearType;
import com.bootcamp.demo.data.save.gear.GearSaveData;
import lombok.Getter;

import java.util.ArrayList;

public class inventorySaveData {
    @Getter
    private ObjectMap<GearType, String> availableSkins;

}
