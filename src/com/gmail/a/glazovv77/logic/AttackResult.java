package com.gmail.a.glazovv77.logic;

import com.gmail.a.glazovv77.core.entity.Predator;
import com.gmail.a.glazovv77.core.world.Coords;

import java.util.Map;
import java.util.Set;

/*
Класс объект-контейнер (DTO — Data Transfer Object)
 */
public class AttackResult {

    private final Set<Coords> deadHerbivores;
    private final Map<Coords, Predator> predatorsToMove;
    private final Set<Predator> predatorsThatKilled;

    public AttackResult(Set<Coords> deadHerbivores, Map<Coords, Predator> predatorsToMove, Set<Predator> predatorsThatKilled) {
        this.deadHerbivores = deadHerbivores;
        this.predatorsToMove = predatorsToMove;
        this.predatorsThatKilled = predatorsThatKilled;
    }

    public Set<Coords> getDeadHerbivores() {
        return deadHerbivores;
    }

    public Map<Coords, Predator> getPredatorsToMove() {
        return predatorsToMove;
    }

    public Set<Predator> getPredatorsThatKilled() {
        return predatorsThatKilled;
    }
}
