package com.gmail.a.glazovv77.core.world;

import com.gmail.a.glazovv77.core.entity.*;

import java.util.*;

public class World {

    private final int columnCount;
    private final int rowCount;

    private final Map<Coordinates, Entity> entities = new HashMap<>();

    public World(int columnCount, int rowCount) {
        this.columnCount = columnCount;
        this.rowCount = rowCount;
    }

    public int getColumnCount() {
        return columnCount;
    }

    public int getRowCount() {
        return rowCount;
    }

    public void setEntity(Coordinates coordinates, Entity entity) {
        validate(coordinates);
        entities.put(coordinates, entity);
    }

    public void removeEntity(Coordinates coords) {
        validate(coords);
        entities.remove(coords);
    }

    public void moveEntity(Coordinates from, Coordinates to) {
        validate(from);
        validate(to);

        Entity entity = getEntity(from);

        if (entity == null) {
            return;
        }
        if(entity instanceof Creature creature) {
            creature.setCoordinates(to);
        }

        removeEntity(from);
        setEntity(to, entity);
    }

    public boolean isCellEmpty(Coordinates coords) {
        validate(coords);
        return !entities.containsKey(coords);
    }

    public Entity getEntity(Coordinates coords) {
        validate(coords);
        return entities.get(coords);
    }

    public Set<Map.Entry<Coordinates, Entity>> getEntries() {
        return Collections.unmodifiableSet(entities.entrySet());
    }

    public static Coordinates getRandomEmptyCoords(World world) {
        Random random = new Random();
        Coordinates coords;

        do {
            int row = random.nextInt(world.getRowCount()) + 1;
            int column = random.nextInt(world.getColumnCount()) + 1;
            coords = new Coordinates(row, column);
        } while (!world.isCellEmpty(coords));

        return coords;
    }

    private void validate(Coordinates coords) {
        if(coords == null) {
            throw new IllegalArgumentException("Координата не может быть null");
        }
        if(!isWithinBounds(coords)) {
            throw new IllegalArgumentException("Координата за пределами карты: " + coords);
        }
    }

    public boolean isWithinBounds(Coordinates coords) {
        return coords.row() > 0 && coords.row() <= rowCount &&
                coords.column() > 0 && coords.column() <= columnCount;
    }
}
