package com.gmail.a.glazovv77.logic;

import com.gmail.a.glazovv77.core.entity.Entity;
import com.gmail.a.glazovv77.core.entity.Herbivore;
import com.gmail.a.glazovv77.core.entity.Predator;
import com.gmail.a.glazovv77.core.world.World;
import com.gmail.a.glazovv77.core.world.Coords;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/*
Класс отвечает за сбор всех запросов на перемещение существ в мире
 */
public class MovementHandler {

    private final World world;

    public MovementHandler(World world) {
        this.world = world;
    }

    // Собирает все запросы на перемещение от всех существ в мире и возвращает их в виде списка объектов Move
    public List<Move> collectMoves(AttackResult attackResult) {

        // Список всех ходов, которые существа хотят сделать
        List<Move> moves = new ArrayList<>();

        // Снова проходим по всем сущностям
        for (var entry : world.getEntries()) {
            Coords sourceCoords = entry.getKey();
            Entity entity = entry.getValue();

            // Хищник, который уже убил, не должен ходить второй раз
            if (entity instanceof Predator && attackResult.getPredatorsThatKilled().contains(entity)) {
                continue;
            }

            // Если это травоядное — оно делает свой ход
            if (entity instanceof Herbivore herbivore) {

                // Доступные клетки для хода
                Set<Coords> availableMoveCells = herbivore.getAvailableMoveCells(world);

                // Куда травоядное хочет пойти
                Coords targetCoords = herbivore.makeMove(availableMoveCells);

                // Если ход существует и не равен текущей клетке
                if (targetCoords != null && !targetCoords.equals(sourceCoords)) {

                    // Травоядное не может идти в клетку, где кто-то умер
                    if (!attackResult.getDeadHerbivores().contains(targetCoords)) {
                        moves.add(new Move(sourceCoords, targetCoords));
                    }
                }

                // Если это хищник (который не убивал)
            } else if (entity instanceof Predator predator) {

                // Доступные клетки
                Set<Coords> availableMoveCells = predator.getAvailableMoveCells(world);

                // Выбор клетки
                Coords targetCoords = predator.makeMove(availableMoveCells);

                // Если ход валиден — добавляем
                if (targetCoords != null && !targetCoords.equals(sourceCoords)) {
                    moves.add(new Move(sourceCoords, targetCoords));
                }
            }
        }

        // Добавляем ходы хищников, которые убили травоядных
        for (var entry : attackResult.getPredatorsToMove().entrySet()) {

            // Клетка, куда нужно переместиться
            Coords targetCell = entry.getKey();

            // Хищник, который должен туда пойти
            Predator predator = entry.getValue();

            // Добавляем ход
            moves.add(new Move(predator.getCoords(), targetCell));
        }
        return moves;
    }
}
