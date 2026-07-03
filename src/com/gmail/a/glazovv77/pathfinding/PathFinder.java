package com.gmail.a.glazovv77.pathfinding;

import com.gmail.a.glazovv77.core.entity.Entity;
import com.gmail.a.glazovv77.core.world.World;
import com.gmail.a.glazovv77.core.world.Coords;
import com.gmail.a.glazovv77.core.world.CoordsShift;

import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.Map;
import java.util.Queue;
import  java.util.List;

/*
Класс отвечает за поиск кратчайшего пути между двумя клетками в мире.
Реализует алгоритм поиск в ширину BFS для нахождения оптимального маршрута с учётом проходимости клеток.
 */
public class PathFinder{

    private static final List<CoordsShift> DIRECTIONS = List.of(
            new CoordsShift(1, 0),
            new CoordsShift(1, -1),

            new CoordsShift(0, -1),
            new CoordsShift(-1, -1),

            new CoordsShift(-1, 0),
            new CoordsShift(-1, 1),

            new CoordsShift(0, 1),
            new CoordsShift(1, 1)
    );

    // Находит кратчайший путь от стартовой клетки до целевой и возвращает первый шаг
    public Coords findPath(Coords start,
                           Coords target,
                           World world,
                           PassabilityStrategy passabilityStrategy) {

        Queue<Coords> queue = new ArrayDeque<>();
        Map<Coords, Integer> distance = new HashMap<>();

        queue.add(target);
        distance.put(target, 0);

        while (!queue.isEmpty()) {
            Coords current = queue.poll();

            for (CoordsShift direction : DIRECTIONS) {

                if (!current.canShift(direction)) {
                    continue;
                }
                Coords neighbor = current.shift(direction);
                Entity entity = world.getEntity(neighbor);

                if(!neighbor.equals(start) && !passabilityStrategy.apply(neighbor, entity, target)) {
                    continue;
                }

                if (!distance.containsKey(neighbor)) {
                    queue.add(neighbor);
                    distance.put(neighbor, distance.get(current) + 1);
                }
            }
        }

        if(!distance.containsKey(start)) {
            return null;
        }
        return findBestNeighbor(start, distance);
    }

    // Находит среди соседей стартовой клетки ту, которая ближе всего к цели
    private Coords findBestNeighbor(Coords start, Map<Coords, Integer> distance) {
        Coords bestNeighbor = null;
        int bestDistance = Integer.MAX_VALUE;

        for (CoordsShift direction : DIRECTIONS) {

            if(!start.canShift(direction)) {
                continue;
            }

            Coords neighbor = start.shift(direction);

            if(!distance.containsKey(neighbor)) {
                continue;
            }

            int distanceNeighbor = distance.get(neighbor);

            if(distanceNeighbor < bestDistance) {
                bestDistance = distanceNeighbor;
                bestNeighbor = neighbor;
            }
        }
        return bestNeighbor;
    }
}
