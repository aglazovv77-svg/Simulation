package com.gmail.a.glazovv77.core.entity;

import com.gmail.a.glazovv77.core.world.Coordinates;
import com.gmail.a.glazovv77.core.world.World;

public class Predator extends Creature {

    public static final int PREDATOR_HP = 50;
    public static final int PREDATOR_SPEED = 3;
    public static final int PREDATOR_ATTACK = 10;

    private final int attackDamage;

    public Predator(Coordinates coordinates) {
        super(coordinates, Herbivore.class);
        this.healthPoints = PREDATOR_HP;
        this.speed = PREDATOR_SPEED;
        this.attackDamage = PREDATOR_ATTACK;
    }

    @Override
    protected void interact(Coordinates targetCoordinates, World world) {

        Entity entity = world.getEntity(targetCoordinates);
        if (entity instanceof Herbivore herbivore) {
            herbivore.takeDamage(attackDamage);
            if (herbivore.getHealthPoints() <= 0) {
                world.removeEntity(targetCoordinates);
            }
        }
    }

    @Override
    public String getSprite() {
        return "🐺";
    }
}
