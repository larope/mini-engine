package com.bootcamp.demo.data.save.pets;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.*;
import com.bootcamp.demo.managers.API;
import com.bootcamp.demo.managers.RandomData;
import lombok.Getter;
import lombok.Setter;

public class PetsSaveData implements Json.Serializable {
    @Getter
    private final IntMap<PetSaveData> pets = new IntMap<>();

    @Override
    public void write(Json json) {
        setRandomPets();
        for (IntMap.Entry<PetSaveData> entry : pets) {
            json.writeValue(String.valueOf(entry.key), entry.value);
        }
    }

    @Override
    public void read(Json json, JsonValue jsonValue) {
        pets.clear();

        for (JsonValue value : jsonValue) {
            final PetSaveData data = json.readValue(PetSaveData.class, value);
            pets.put(pets.size, data);
        }
    }

    private void setRandomPets(){
        for (int i = 0; i < 5; i++) {
            PetSaveData pet = API.get(RandomData.class).getRandomPet();
            pet.setStats(API.get(RandomData.class).getRandomStats(new Vector2(1500, 6000), new Vector2(0,1)));
            pets.put(i, pet);
        }
    }
}
