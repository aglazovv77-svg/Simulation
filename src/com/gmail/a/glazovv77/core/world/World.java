package com.gmail.a.glazovv77.core.world;

import com.gmail.a.glazovv77.factory.EntityFactory;
import com.gmail.a.glazovv77.core.config.GameConfig;
import com.gmail.a.glazovv77.core.entity.*;

import java.util.*;

/*
Класс является центральным хранилищем игрового мира.
Управляет всеми сущностями (существами, травой, деревьями, камнями),
их координатами и предоставляет API для взаимодействия с игровой картой
 */
public class World {

    private final Map<Coords, Entity> entities = new HashMap<>();
    private final Map<Coords, String> highlightedCells = new HashMap<>();

    // Размещает сущность на карте по указанным координатам.
    public void setEntity(Coords coords, Entity entity) {
        entity.setCoords(coords);
        entities.put(coords, entity);
    }

    // Удаляет сущность
    public void removeEntity(Coords coords) {
        entities.remove(coords);
    }

    // Перемещает сущность с одной клетки на другую
    public void moveEntity(Coords from, Coords to) {
        Entity entity = getEntity(from);

        if (entity == null) {
            return;
        }

        removeEntity(from);
        setEntity(to, entity);
    }

    // Свободна ли клетка
    public boolean isCellEmpty(Coords coords) {
        return !entities.containsKey(coords);
    }

    // Возвращает сущность, находящуюся на указанных координатах
    public Entity getEntity(Coords coords) {
        return entities.get(coords);
    }

    // коллекция существ с координатами
    public Set<Map.Entry<Coords, Entity>> getEntries() {
        return Collections.unmodifiableSet(entities.entrySet());
    }

    // Первоначальная расстановка всех сущностей на карте при создании мира
    public void setupEntitiesPosition(EntityFactory entityFactory) {
        for (int i = 0; i < GameConfig.INITIAL_HERBIVORE; i++) {
            Coords coords = getRandomEmptyCoords();
            setEntity(coords, entityFactory.createHerbivore(coords));
        }

        for (int i = 0; i < GameConfig.INITIAL_PREDATOR; i++) {
            Coords coords = getRandomEmptyCoords();
            setEntity(coords, entityFactory.createPredator(coords));
        }

        for (int i = 0; i < GameConfig.INITIAL_GRASS; i++) {
            Coords coords = getRandomEmptyCoords();
            setEntity(coords, entityFactory.createGrass(coords));
        }

        for (int i = 0; i < GameConfig.INITIAL_ROCK; i++) {
            Coords coords = getRandomEmptyCoords();
            setEntity(coords, entityFactory.createRock(coords));
        }

        for (int i = 0; i < GameConfig.INITIAL_TREE; i++) {
            Coords coords = getRandomEmptyCoords();
            setEntity(coords, entityFactory.createTree(coords));
        }
    }

    // Генерирует случайные координаты, гарантируя, что клетка пуста
    public Coords getRandomEmptyCoords() {
        Random random = new Random();
        Coords coords;

        do {
            Integer row = random.nextInt(GameConfig.WORLD_HEIGHT) + 1;
            Integer col = random.nextInt(GameConfig.WORLD_WIDTH) + 1;
            coords = new Coords(row, col);
        } while (!isCellEmpty(coords));

        return coords;
    }

    // Возвращает неизменяемую карту подсвеченных клеток
    public Map<Coords, String> getHighlightedCells() {
        return Collections.unmodifiableMap(highlightedCells);
    }

    // Добавляет клетку в список подсвеченных с указанием типа подсветки
    public void highlight(Coords cell, String type) {
        highlightedCells.put(cell, type);
    }

// Очищает все подсветки
    public void clearHighlights() {
        highlightedCells.clear();
    }
}
