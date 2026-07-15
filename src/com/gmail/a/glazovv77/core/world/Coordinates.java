package com.gmail.a.glazovv77.core.world;

public record Coordinates(int row, int column) {

    public Coordinates shift(Coordinates shiftCoordinates) {
        return new Coordinates(this.row + shiftCoordinates.row(), this.column + shiftCoordinates.column());
    }
}
