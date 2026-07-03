package com.gmail.a.glazovv77;

import com.gmail.a.glazovv77.core.config.ApplicationConfig;
import com.gmail.a.glazovv77.engine.Simulation;

public class Main {

    public static void main(String[] args) {

        Simulation simulation = ApplicationConfig.createSimulation();
        simulation.run();
    }
}
