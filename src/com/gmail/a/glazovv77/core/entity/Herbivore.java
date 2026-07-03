package com.gmail.a.glazovv77.core.entity;

import com.gmail.a.glazovv77.core.world.Coords;
import com.gmail.a.glazovv77.pathfinding.PathFinder;
import com.gmail.a.glazovv77.core.world.World;

import java.util.Set;

public class Herbivore extends Creature {

    public boolean wasAttackedThisTurn = false;
    public boolean wasAttackedLastTurn = false;

    public Herbivore(Coords coords, int healthPoints, int speed, PathFinder pathFinder, World world) {
        super(coords, healthPoints, speed, pathFinder, world);
    }

    @Override
    public Coords makeMove(Set<Coords> availableCells) {

        if (wasAttackedLastTurn) {
            wasAttackedLastTurn = false; // Сбрасываем флаг (один раз убежали)
            return findEscapeMove(availableCells);
        }

        Coords bestCoordsGrass = findClosestEntityByClass(availableCells, Grass.class);

        return moveTowardsTarget(
                bestCoordsGrass,
                availableCells,
                (neighbor, entity, target) ->
                        neighbor.equals(target) ||
                        entity == null ||
                        entity instanceof Grass
        );
    }

    private Coords findEscapeMove(Set<Coords> availableCells) {
        // Если нет доступных клеток - стоим на месте
        if (availableCells == null || availableCells.isEmpty()) {
            return this.getCoords();
        }
        // Просто выбираем первую доступную клетку (не свою текущую)
        for (Coords cell : availableCells) {
            if (!cell.equals(this.getCoords())) {
                return cell;
            }
        }

        return this.getCoords();
    }

    @Override
    protected boolean isCellAvailableForMove(Coords coords, World world) {
        Entity entity = world.getEntity(coords);
        return entity == null || entity instanceof Grass;
    }
}

