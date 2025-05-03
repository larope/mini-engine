package com.bootcamp.demo.managers;

import com.bootcamp.demo.events.core.EventHandler;
import com.bootcamp.demo.events.core.EventListener;
import com.bootcamp.demo.events.core.EventModule;
import com.bootcamp.demo.events.example.LootedEvent;

public class HuntingManager implements EventListener {

    public HuntingManager () {
        API.get(EventModule.class).registerListener(this);
    }

    @EventHandler
    public void onLootedEvent (LootedEvent event) {
        // todo loot maybe adono
        System.out.println();
    }
}
