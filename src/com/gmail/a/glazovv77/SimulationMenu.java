package com.gmail.a.glazovv77;

import com.gmail.a.glazovv77.core.world.World;
import com.gmail.a.glazovv77.factory.EntityFactory;
import com.gmail.a.glazovv77.input.ConsoleInputHandler;
import com.gmail.a.glazovv77.input.InputHandler;
import com.gmail.a.glazovv77.logic.*;
import com.gmail.a.glazovv77.rendering.ConsoleRenderer;
import com.gmail.a.glazovv77.rendering.Renderer;

import java.util.List;

public class SimulationMenu {

    public static Simulation create(World world) {

        Renderer renderer = new ConsoleRenderer(world);

        EntityFactory entityFactory = new EntityFactory();
        RespawnManager respawnManager = new RespawnManager(world, entityFactory);

        InputHandler iInputHandler = new ConsoleInputHandler();

        List<Action> initAction = List.of(
                new InitAction(respawnManager),
                new RenderAction(renderer)
        );

        List<Action> turnAction = List.of(
                new MoveAction(),
                new RespawnAction(respawnManager),
                new RenderAction(renderer)
        );

        return new Simulation(world, iInputHandler, initAction, turnAction);
    }
}
