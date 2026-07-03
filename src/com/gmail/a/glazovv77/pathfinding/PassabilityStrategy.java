package com.gmail.a.glazovv77.pathfinding;

import com.gmail.a.glazovv77.core.entity.Entity;
import com.gmail.a.glazovv77.core.world.Coords;

/*
Функциональный интерфейс, определяет стратегию проверки проходимости клеток для поиска пути
 */
@FunctionalInterface
public interface PassabilityStrategy {
    // проверяет может ли существо пройти через соседнюю клетку к цели
    boolean apply (Coords neighbor, Entity entity, Coords target);
}
