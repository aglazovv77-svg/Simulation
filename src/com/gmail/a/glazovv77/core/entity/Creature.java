package com.gmail.a.glazovv77.core.entity;

import com.gmail.a.glazovv77.core.world.Coords;
import com.gmail.a.glazovv77.core.world.World;
import com.gmail.a.glazovv77.core.world.CoordsShift;
import com.gmail.a.glazovv77.pathfinding.PassabilityStrategy;
import com.gmail.a.glazovv77.pathfinding.PathFinder;

import java.util.*;

public abstract class Creature extends Entity {

    private int healthPoints;
    private final int speed;

    protected final PathFinder pathFinder;
    protected final World world;

    public Creature(Coords coords, int healthPoints, int speed, PathFinder pathFinder, World world) {
        super(coords);
        this.healthPoints = healthPoints;
        this.speed = speed;
        this.pathFinder = pathFinder;
        this.world = world;

    }

    public int getHealthPoints() {
        return healthPoints;
    }

    public int getSpeed() {
        return speed;
    }

    public boolean takeDamage(int amount) {

        healthPoints -= amount;
        return getHealthPoints() > 0;
    }

    // множество клеток куда можно пойти
    public Set<Coords> getAvailableMoveCells (World world) {
        Set<Coords> result = new HashSet<>();
        Queue<Coords> queue = new ArrayDeque<>();
        Map<Coords, Integer> distance = new HashMap<>();

        queue.add(this.getCoords());
        distance.put(this.getCoords(), 0);
        result.add(this.getCoords());

        while (!queue.isEmpty()) {
            Coords current = queue.poll();
            int currentDistance = distance.get(current);

            if (currentDistance >= getSpeed()) {
                continue;
            }

            for (CoordsShift shift : getCreatureMoves()) {
                if (current.canShift(shift)) {
                    Coords newCoords = current.shift(shift);

                    if(!distance.containsKey(newCoords) && isCellAvailableForMove(newCoords, world)) {
                        distance.put(newCoords, currentDistance + 1);
                        queue.add(newCoords);
                        result.add(newCoords);
                    }
                }
            }
        }
       return result;
   }

   // Проверка, может ли существо шагнуть на конкретную клетку
    protected boolean isCellAvailableForMove(Coords coords, World world) {
        return world.isCellEmpty(coords);
    }

    // возможные ходы существ
    protected Set<CoordsShift> getCreatureMoves() {
        return new HashSet<>(Arrays.asList(
                new CoordsShift(1, 0),
                new CoordsShift(1, -1),

                new CoordsShift(0, -1),
                new CoordsShift(-1, -1),

                new CoordsShift(-1, 0),
                new CoordsShift(-1, 1),

                new CoordsShift(0, 1),
                new CoordsShift(1, 1)
        ));
    }

    // поиск ближайшей еды
    protected Coords findClosestEntityByClass(Set<Coords> availableCells, Class<? extends Entity> targetClass) {
        Coords bestCoords = null;
        int bestDistance = Integer.MAX_VALUE;
        Coords currentCoords = this.getCoords();

        for (var entry : this.world.getEntries()) {
            var entity = entry.getValue();
            if (targetClass.isInstance(entity)) {
                Coords targetCoords = entry.getKey();

                if (!availableCells.contains(targetCoords)) {
                    continue;
                }

                int dRow = Math.abs(currentCoords.getRow() - targetCoords.getRow());
                int dCol = Math.abs(currentCoords.getCol() - targetCoords.getCol());
                int distance = dRow * dRow + dCol * dCol;

                if (distance < bestDistance) {
                    bestDistance = distance;
                    bestCoords = targetCoords;
                }
            }
        }
        return bestCoords;
    }

    // получение первого шага к цели
    protected Coords moveTowardsTarget(Coords target, Set<Coords> availableCells, PassabilityStrategy passabilityStrategy) {

        if (target == null) {
            if (availableCells.isEmpty()) {
                return this.getCoords();
            }
            return Coords.getRandomCoords(availableCells);
        }

        // Ищем путь к траве
        Coords nextStep = pathFinder.findPath(
                this.getCoords(),
                target,
                world,
                passabilityStrategy);

        // Если следующий шаг доступен - идем
        if (nextStep != null && availableCells.contains(nextStep)) {
            return nextStep;
        }

        // Иначе случайное движение
        if (availableCells.isEmpty()) {
            return this.getCoords();
        }
        return Coords.getRandomCoords(availableCells);

    }

    abstract Coords makeMove(Set<Coords> set);
}
