package com.gmail.a.glazovv77;

import com.gmail.a.glazovv77.core.world.World;

public class Main {

    public static void main(String[] args) {

        World world = new World(20, 20);

        Simulation simulation = SimulationMenu.create(world);
        simulation.start();
    }
}
