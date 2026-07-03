package com.gmail.a.glazovv77.factory;

import com.gmail.a.glazovv77.core.config.GameConfig;
import com.gmail.a.glazovv77.core.entity.*;
import com.gmail.a.glazovv77.core.world.World;
import com.gmail.a.glazovv77.core.world.Coords;
import com.gmail.a.glazovv77.pathfinding.PathFinder;

/*
Класс фабрика создания существ
 */
public class EntityFactory {

    private final PathFinder pathFinder;
    private final World world;

    public EntityFactory(PathFinder pathFinder, World world) {
        this.pathFinder = pathFinder;
        this.world = world;
    }

    public Tree createTree(Coords coords) {
        return new Tree(coords);
    }

    public Rock createRock(Coords coords) {
        return new Rock(coords);
    }

    public Grass createGrass(Coords coords) {
        return new Grass(coords);
    }

    public Herbivore createHerbivore(Coords coords) {
        return  new Herbivore(coords, GameConfig.HERBIVORE_HP, GameConfig.HERBIVORE_SPEED, pathFinder, world);
    }

    public Predator createPredator(Coords coords) {
        return  new Predator(coords, GameConfig.PREDATOR_HP, GameConfig.PREDATOR_SPEED, pathFinder, world, GameConfig.PREDATOR_ATTACK);
    }
}
