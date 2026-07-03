package com.gmail.a.glazovv77.logic;

import com.gmail.a.glazovv77.core.entity.Entity;
import com.gmail.a.glazovv77.core.entity.Grass;
import com.gmail.a.glazovv77.core.entity.Herbivore;
import com.gmail.a.glazovv77.core.entity.Predator;
import com.gmail.a.glazovv77.core.world.World;
import com.gmail.a.glazovv77.core.world.Coords;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
Класс отвечает за разрешение конфликтов при перемещениях существ в мире
 */
public class ConflictResolver {

    private final World world;

    public ConflictResolver(World world) {
        this.world = world;
    }

    // Принимает список всех запрошенных перемещений и возвращает список разрешённых (без конфликтов)
    public List<Move> resolveConflicts(List<Move> moves) {

        // Группируем ходы по целевым клеткам
        Map<Coords, List<Move>> targetCellMap = new HashMap<>();

        for (var move : moves) {
            Coords cell = move.to();

            // Если в карте нет списка — создаём
            if (!targetCellMap.containsKey(cell)) {
                targetCellMap.put(cell, new ArrayList<>());
            }

            // Добавляем ход в список
            targetCellMap.get(cell).add(move);
        }

        // Список разрешённых ходов
        List<Move> resolvedMoves = new ArrayList<>();

        // Разрешаем конфликты
        for (var entry : targetCellMap.entrySet()) {

            // Клетка-цель
            Coords cell = entry.getKey();

            // Ходы, которые хотят попасть в эту клетку
            List<Move> movesToCell = entry.getValue();

            // Что стоит в клетке сейчас
            Entity targetEntity = world.getEntity(cell);

            // Если ход всего один
            if (movesToCell.size() == 1) {

                // Единственный ход
                Move onlyMove = movesToCell.getFirst();

                // Кто двигается
                Entity mover = world.getEntity(onlyMove.from());

                // Если существа нет — пропускаем
                if (mover == null) continue;

                // Травоядное ест траву
                if (mover instanceof Herbivore && targetEntity instanceof Grass) {
                    world.removeEntity(cell);
                    resolvedMoves.add(onlyMove);
                    world.highlight(cell, "eat");
                    continue;
                }

                // Если клетка пустая — разрешаем ход
                if (targetEntity == null) {
                    resolvedMoves.add(onlyMove);
                }
                continue;
            }

            // Если ходов несколько — разделяем по типам
            List<Move> predatorMoves = new ArrayList<>();
            List<Move> herbivoreMoves = new ArrayList<>();

            for (var move : movesToCell) {
                Entity mover = world.getEntity(move.from());
                if (mover instanceof Predator) predatorMoves.add(move);
                if (mover instanceof Herbivore) herbivoreMoves.add(move);
            }

            // Если в клетке трава — травоядное ест её
            if (targetEntity instanceof Grass && !herbivoreMoves.isEmpty()) {
                Move herbivoreMove = herbivoreMoves.getFirst();
                world.removeEntity(cell);
                resolvedMoves.add(herbivoreMove);
                world.highlight(cell, "eat");
                continue;
            }

            // Если клетка пустая — хищники имеют приоритет
            if (targetEntity == null) {

                if (!predatorMoves.isEmpty()) {
                    resolvedMoves.add(predatorMoves.getFirst());
                } else if (!herbivoreMoves.isEmpty()) {
                    resolvedMoves.add(herbivoreMoves.getFirst());
                }
            }
        }
        return resolvedMoves;

    }
}
