package com.gmail.a.glazovv77.factory;

import com.gmail.a.glazovv77.core.entity.*;
import com.gmail.a.glazovv77.core.world.Coordinates;

public class EntityFactory {

    public Tree createTree() {
        return new Tree();
    }

    public Rock createRock() {
        return new Rock();
    }

    public Grass createGrass() {
        return new Grass();
    }

    public Herbivore createHerbivore(Coordinates coordinates) {
        return new Herbivore(coordinates);
    }

    public Predator createPredator(Coordinates coordinates) {
        return new Predator(coordinates);
    }
}
