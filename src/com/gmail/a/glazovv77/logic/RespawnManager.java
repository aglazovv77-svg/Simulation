package com.gmail.a.glazovv77.logic;

import com.gmail.a.glazovv77.core.entity.Entity;
import com.gmail.a.glazovv77.core.entity.Grass;
import com.gmail.a.glazovv77.core.entity.Herbivore;
import com.gmail.a.glazovv77.factory.EntityFactory;
import com.gmail.a.glazovv77.core.world.World;
import com.gmail.a.glazovv77.core.world.Coordinates;

import java.util.function.Function;

public class RespawnManager {

    public static final int INITIAL_GRASS = 15;
    public static final int INITIAL_HERBIVORE = 15;

    public static final int INITIAL_PREDATOR = 5;
    public static final int INITIAL_ROCK = 10;
    public static final int INITIAL_TREE = 10;

    public static final int GRASS_SPAWN_THRESHOLD = 5;
    public static final int HERBIVORE_SPAWN_THRESHOLD = 3;

    private final World world;
    private final EntityFactory entityFactory;

    public RespawnManager(World world, EntityFactory entityFactory) {
        this.world = world;
        this.entityFactory = entityFactory;
    }

    public void setupEntitiesPosition() {

        spawn(coordinates ->  entityFactory.createGrass(), INITIAL_GRASS);
        spawn(coordinates ->  entityFactory.createRock(), INITIAL_ROCK);
        spawn(coordinates ->  entityFactory.createTree(), INITIAL_TREE);
        spawn(entityFactory::createHerbivore, INITIAL_HERBIVORE);
        spawn(entityFactory::createPredator, INITIAL_PREDATOR);
    }

    private void spawn(Function<Coordinates, Entity> mapper, int count) {
        for (int i = 0; i < count; i++) {
            Coordinates coordinates = World.getRandomEmptyCoords(world);
            Entity entity = mapper.apply(coordinates);
            world.setEntity(coordinates, entity);
        }
    }

    protected void respawnEntities() {

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
        int grassToAdd = INITIAL_GRASS - countGrass;
        if (countGrass <= GRASS_SPAWN_THRESHOLD) {
            for (int i = 0; i < grassToAdd; i++) {
                Coordinates coords = World.getRandomEmptyCoords(world);
                Grass grass = entityFactory.createGrass();
                world.setEntity(coords, grass);
            }

        }
        int herbivoreToAdd = INITIAL_HERBIVORE - countHerbivore;
        if (countHerbivore <= HERBIVORE_SPAWN_THRESHOLD) {
            for (int i = 0; i < herbivoreToAdd; i++) {
                Coordinates coords = World.getRandomEmptyCoords(world);
                Herbivore herbivore = entityFactory.createHerbivore(coords);
                world.setEntity(coords, herbivore);
            }
        }
    }
}
