package com.gmail.a.glazovv77.pathfinding;

import com.gmail.a.glazovv77.core.utils.BoardCoordinates;
import com.gmail.a.glazovv77.core.entity.Entity;
import com.gmail.a.glazovv77.core.world.Coordinates;
import com.gmail.a.glazovv77.core.world.World;

import java.util.*;

public class BFSPathFinder implements PathFinder {

    private static final List<Coordinates> SHIFTS = List.of(
            new Coordinates(1, 0),
            new Coordinates(1, -1),

            new Coordinates(0, -1),
            new Coordinates(-1, -1),

            new Coordinates(-1, 0),
            new Coordinates(-1, 1),

            new Coordinates(0, 1),
            new Coordinates(1, 1)
    );

    @Override
    public List<Coordinates> find(Coordinates start, World world, Class<? extends Entity> targetClass) {

        BoardCoordinates boardCoordinates = new BoardCoordinates(world);

        Queue<Coordinates> queue = new ArrayDeque<>();
        Set<Coordinates> visited = new HashSet<>();
        Map<Coordinates, Coordinates> previous = new HashMap<>();

        queue.add(start);
        visited.add(start);
        previous.put(start, null);

        while (!queue.isEmpty()) {
            Coordinates current = queue.poll();

            Entity entity = world.getEntity(current);
            if (entity != null && targetClass.isAssignableFrom(entity.getClass())) {
                return reconstructPath(previous, current);
            }

            for (Coordinates direction : SHIFTS) {
                if (!boardCoordinates.canShift(current, direction)) {
                    continue;
                }

                Coordinates neighbor = current.shift(direction);

                if (visited.contains(neighbor)) {
                    continue;
                }

                Entity neighborEntity = world.getEntity(neighbor);
                if (neighborEntity != null && !targetClass.isAssignableFrom(neighborEntity.getClass())) {
                    continue; // клетка занята другой сущностью — пропускаем
                }

                visited.add(neighbor);
                queue.add(neighbor);
                previous.put(neighbor, current);
            }
        }

        return List.of();
    }

    private List<Coordinates> reconstructPath(Map<Coordinates, Coordinates> previous, Coordinates target) {
        List<Coordinates> path = new ArrayList<>();
        Coordinates current = target;

        while (current != null) {
            path.add(current);
            current = previous.get(current);
        }

        path.remove(path.size() - 1);
        Collections.reverse(path);
        return path;
    }
}
