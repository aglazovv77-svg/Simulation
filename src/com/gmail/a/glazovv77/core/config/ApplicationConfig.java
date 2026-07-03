package com.gmail.a.glazovv77.core.config;

import com.gmail.a.glazovv77.core.world.World;
import com.gmail.a.glazovv77.engine.IActionEngine;
import com.gmail.a.glazovv77.engine.Simulation;
import com.gmail.a.glazovv77.factory.EntityFactory;
import com.gmail.a.glazovv77.input.ConsoleInputHandler;
import com.gmail.a.glazovv77.input.IInputHandler;
import com.gmail.a.glazovv77.logic.*;
import com.gmail.a.glazovv77.pathfinding.PathFinder;
import com.gmail.a.glazovv77.rendering.ConsoleRenderer;
import com.gmail.a.glazovv77.rendering.IRenderer;

public class ApplicationConfig {

    public static Simulation createSimulation() {

        World world = new World();
        IRenderer renderer = new ConsoleRenderer(world);

        PathFinder pathFinder = new PathFinder();
        EntityFactory entityFactory = new EntityFactory(pathFinder, world);

        AttackHandler attackHandler = new AttackHandler(world);
        MovementHandler movementHandler = new MovementHandler(world);
        ConflictResolver conflictResolver = new ConflictResolver(world);
        RespawnManager respawnManager = new RespawnManager(world, entityFactory);

        IActionEngine actionEngine = new Actions(
                                            world,
                                            renderer,
                                            entityFactory,
                                            attackHandler,
                                            movementHandler,
                                            conflictResolver,
                                            respawnManager);

        IInputHandler iInputHandler = new ConsoleInputHandler();

        return new Simulation(actionEngine, iInputHandler);
    }
}
