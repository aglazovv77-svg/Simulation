package com.gmail.a.glazovv77.core.utils;

import com.gmail.a.glazovv77.core.world.Coordinates;
import com.gmail.a.glazovv77.core.world.World;

public class BoardCoordinates {

    private final World world;

    public BoardCoordinates(World world) {
        this.world = world;
    }

    public boolean canShift(Coordinates currentCoordinates, Coordinates shiftCoordinates) {
        int newRow = currentCoordinates.row() + shiftCoordinates.row();
        int newColumn = currentCoordinates.column() + shiftCoordinates.column();

        return newRow > 0 && newRow <= world.getRowCount() &&
                newColumn > 0 && newColumn <= world.getColumnCount();
    }
}
