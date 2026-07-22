package com.gmail.a.glazovv77.core.entity;

import com.gmail.a.glazovv77.core.world.Coordinates;
import com.gmail.a.glazovv77.core.world.World;
import com.gmail.a.glazovv77.pathfinding.BFSPathFinder;
import com.gmail.a.glazovv77.pathfinding.PathFinder;

import java.util.*;

public abstract class Creature extends Entity {

    protected int healthPoints;
    protected int speed;

    protected Coordinates coordinates;
    protected final PathFinder pathFinder;
    protected final Class<? extends Entity> food;

    public Creature(Coordinates coordinates, Class<? extends Entity> food) {
        this.coordinates = coordinates;
        this.food = food;
        this.pathFinder = new BFSPathFinder();
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    public int getHealthPoints() {
        return healthPoints;
    }

    public int getSpeed() {
        return speed;
    }

    public void makeMove(World world) {
        List<Coordinates> path = pathFinder.find(getCoordinates(), world, food);

        if(path.isEmpty()) {
            return;
        }

        if (path.size() > getSpeed()) {
            Coordinates step = path.get(getSpeed() - 1);
            world.moveEntity(getCoordinates(), step);
        } else {
            if (path.size() >= 2) {
                Coordinates step = path.get(path.size() - 2);
                world.moveEntity(getCoordinates(), step);
            }
            interact(path.get(path.size() - 1), world);
        }
    }

    protected abstract void interact(Coordinates targetCoordinates, World world);

    public void takeDamage(int amount) {
        healthPoints -= amount;
    }
}
