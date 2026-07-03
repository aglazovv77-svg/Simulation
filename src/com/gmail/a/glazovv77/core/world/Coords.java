package com.gmail.a.glazovv77.core.world;

import com.gmail.a.glazovv77.core.config.GameConfig;

import java.util.Objects;
import java.util.Random;
import java.util.Set;

public class Coords {

    private final Integer row;
    private final Integer col;

    public Coords(Integer row, Integer col) {
        this.row = row;
        this.col = col;
    }

    public Integer getCol() {
        return col;
    }

    public Integer getRow() {
        return row;
    }

    // шаг сдвига
    public Coords shift(CoordsShift shift) {
        return new Coords(this.row + shift.rowShift, this.col + shift.colShift);
    }

    // возможность сдвига
    public boolean canShift(CoordsShift shift) {
        int r = row + shift.rowShift;
        int c = col + shift.colShift;

        if((r < 1) || (r > GameConfig.WORLD_HEIGHT)) return false;
        return (c >= 1) && (c <= GameConfig.WORLD_WIDTH);
    }

    // случайным образом выбирает из списка координат одну
    public static Coords getRandomCoords(Set<Coords> set) {
        int size = set.size();
        if(size == 0) return null;
        int item = new Random().nextInt(size);
        int i = 0;
        for (Coords coords : set) {
            if(i == item) return coords;
            i++;
        }
        return null;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Coords coords = (Coords) o;
        return Objects.equals(row, coords.row) && Objects.equals(col, coords.col);
    }

    @Override
    public int hashCode() {
        return Objects.hash(row, col);
    }

    @Override
    public String toString() {
        return "Coords{row=%d, col=%d}".formatted(row, col);
    }
}
