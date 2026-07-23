package com.gmail.a.glazovv77.core.entity;

import com.gmail.a.glazovv77.core.world.Coordinates;
import com.gmail.a.glazovv77.core.world.World;

public class Herbivore extends Creature {

    public static final int HERBIVORE_HP = 30;
    public static final int HERBIVORE_SPEED = 2;

    public Herbivore(Coordinates coordinates) {
        super(coordinates, Grass.class);
        this.healthPoints = HERBIVORE_HP;
        this.speed = HERBIVORE_SPEED;
    }

    @Override
    protected void interact(Coordinates targetCoordinates, World world) {
        world.removeEntity(targetCoordinates);
    }

    @Override
    public String getSprite() {
        return "🐇";
    }
}
