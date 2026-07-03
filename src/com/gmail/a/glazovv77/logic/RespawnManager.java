package com.gmail.a.glazovv77.logic;

import com.gmail.a.glazovv77.core.config.GameConfig;
import com.gmail.a.glazovv77.core.entity.Entity;
import com.gmail.a.glazovv77.core.entity.Grass;
import com.gmail.a.glazovv77.core.entity.Herbivore;
import com.gmail.a.glazovv77.factory.EntityFactory;
import com.gmail.a.glazovv77.core.world.World;
import com.gmail.a.glazovv77.core.world.Coords;

/*
Класс отвечает за восстановление популяций сущностей в мире
 */
public class RespawnManager {

    private final World world;
    private final EntityFactory entityFactory;

    public RespawnManager(World world, EntityFactory entityFactory) {
        this.world = world;
        this.entityFactory = entityFactory;
    }

    // Восстанавливает популяции травы и травоядных, если их количество опускается ниже установленного порога
    public void respawnEntities() {

        int countGrass = 0;
        int countHerbivore = 0;

        for (var entry : world.getEntries()) {
            Entity entity = entry.getValue();
            if (entity instanceof Grass) {
                countGrass++;
            }
            if (entity instanceof Herbivore) {
                countHerbivore++;
            }
        }
        int grassToAdd = GameConfig.INITIAL_GRASS - countGrass;
        if (countGrass <= GameConfig.GRASS_SPAWN_THRESHOLD) {
            for (int i = 0; i < grassToAdd; i++) {
                Coords coords = world.getRandomEmptyCoords();
                Grass grass = entityFactory.createGrass(coords);
                world.setEntity(coords, grass);
            }

        }
        int herbivoreToAdd = GameConfig.INITIAL_HERBIVORE - countHerbivore;
        if (countHerbivore <= GameConfig.HERBIVORE_SPAWN_THRESHOLD) {
            for (int i = 0; i < herbivoreToAdd; i++) {
                Coords coords = world.getRandomEmptyCoords();
                Herbivore herbivore = entityFactory.createHerbivore(coords);
                world.setEntity(coords, herbivore);
            }
        }
    }
}
