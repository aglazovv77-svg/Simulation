package com.gmail.a.glazovv77.logic;

import com.gmail.a.glazovv77.core.entity.Entity;
import com.gmail.a.glazovv77.core.entity.Herbivore;
import com.gmail.a.glazovv77.core.entity.Predator;
import com.gmail.a.glazovv77.core.world.World;
import com.gmail.a.glazovv77.core.world.Coords;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/*
Класс отвечает за обработку всех атак в игровом мире
 */
public class AttackHandler {

    private final World world;

    public AttackHandler(World world) {
        this.world = world;
    }

    // Обрабатывает все атаки хищников на травоядных в текущем ходу
    public AttackResult processAttacks() {

        // Множество клеток, где травоядные умерли в этом ходу
        Set<Coords> deadHerbivores = new HashSet<>();

        // Куда должен переместиться хищник после убийства травоядного (клетка → хищник)
        Map<Coords, Predator> predatorsToMove = new HashMap<>();

        // Хищники, которые уже убили и не должны ходить второй раз
        Set<Predator> predatorsThatKilled = new HashSet<>();

        for (var entry : world.getEntries()) {
            Entity entity = entry.getValue();

            if (entity instanceof Predator predator) {

                // Клетки, куда хищник может пойти
                Set<Coords> availableCells = predator.getAvailableMoveCells(world);

                for (Coords targetCell : availableCells) {
                    Entity targetEntity = world.getEntity(targetCell);

                    if (targetEntity instanceof Herbivore herbivore) {
                        boolean isAlive = herbivore.takeDamage(predator.getAttackDamage());

                        // Если травоядное погибло
                        if (!isAlive) {

                            deadHerbivores.add(targetCell);
                            predatorsToMove.put(targetCell, predator);
                            predatorsThatKilled.add(predator);

                            world.highlight(targetCell, "attack");

                        } else {
                            // Травоядное выжило — отмечаем, что его атаковали
                            herbivore.wasAttackedLastTurn = true;
                        }
                        break;
                    }
                }
            }
        }

        return new AttackResult(deadHerbivores, predatorsToMove, predatorsThatKilled);
    }
}
