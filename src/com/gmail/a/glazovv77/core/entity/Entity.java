package com.gmail.a.glazovv77.core.entity;

import com.gmail.a.glazovv77.core.world.Coords;

public abstract class Entity {

    private Coords coords;

    public Entity(Coords coords) {
        this.coords = coords;
    }

    public void setCoords(Coords coords) {
        this.coords = coords;
    }

    public Coords getCoords() {
        return coords;
    }


}
