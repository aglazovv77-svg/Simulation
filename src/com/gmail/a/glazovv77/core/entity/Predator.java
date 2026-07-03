package com.gmail.a.glazovv77.core.entity;

import com.gmail.a.glazovv77.core.world.Coords;
import com.gmail.a.glazovv77.pathfinding.PathFinder;
import com.gmail.a.glazovv77.core.world.World;

import java.util.Set;

public class Predator extends Creature {

    private final int attackDamage;

    public Predator(Coords coords, int healthPoints, int speed, PathFinder pathFinder, World world, int attackDamage) {
        super(coords, healthPoints, speed, pathFinder, world);
        this.attackDamage = attackDamage;
    }

    public int getAttackDamage() {
        return attackDamage;
    }

    @Override
    public Coords makeMove(Set<Coords> availableCells) {

        Coords bestCoordsHerbivore = findClosestEntityByClass(availableCells, Herbivore.class);

        return moveTowardsTarget(
                bestCoordsHerbivore,
                availableCells,
                (neighbor, entity, target) ->
                        neighbor.equals(target) ||
                                entity == null ||
                                entity instanceof Herbivore
        );
    }

    @Override
    protected boolean isCellAvailableForMove(Coords coords, World world) {
        Entity entity = world.getEntity(coords);
        return entity == null || entity instanceof Herbivore;
    }
}
