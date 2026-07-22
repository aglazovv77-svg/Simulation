package com.gmail.a.glazovv77;

import com.gmail.a.glazovv77.core.world.World;
import com.gmail.a.glazovv77.input.InputHandler;
import com.gmail.a.glazovv77.logic.Action;
import com.gmail.a.glazovv77.rendering.Renderer;

import java.io.IOException;
import java.util.List;

public class Simulation {

    private static final int SLEEP_TIME = 1000;
    private static final int PAUSE_SLEEP_TIME = 200;

    private final World world;
    private final InputHandler inputHandler;
    private final Renderer renderer;

    private final List<Action> initActions;
    private final List<Action> turnActions;

    private int turnCount = 0;
    private boolean running = true;
    private boolean exit = false;

    public Simulation(World world, InputHandler inputHandler, Renderer renderer, List<Action> initActions, List<Action> turnActions) {
        this.world = world;
        this.inputHandler = inputHandler;
        this.renderer = renderer;
        this.initActions = initActions;
        this.turnActions = turnActions;
    }

    public void printGreeting() {
        renderer.showGreeting();
    }

    public void nextTurn() {
        for (Action turnAction : turnActions) {
            turnAction.execute(world);
        }
        renderer.showTurnCount(turnCount);
        turnCount++;
    }

    @SuppressWarnings("BusyWait")
    public void startSimulation() {
        running = true;
        renderer.showStartSimulationMessage();

        while (true) {
            try {
                if (System.in.available() > 0) {
                    String command = inputHandler.readCommand();

                    if (inputHandler.isPause(command)) {
                        pauseSimulation();
                        if (exit) {
                            return;
                        }
                    }

                    if (inputHandler.isQuit(command)) {
                        renderer.showExitMessage();
                        exit = true;
                        return;
                    }
                }
            } catch (IOException e) {
                throw new RuntimeException("Ошибка ввода", e);
            }

            if (running) {
                nextTurn();
                renderer.showPausePrompt();

                try {
                    Thread.sleep(SLEEP_TIME);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    throw new RuntimeException("Симуляция прервана", e);
                }
            }
        }
    }

    @SuppressWarnings("BusyWait")
    public void pauseSimulation() {
        nextTurn();
        running = false;
        renderer.showPauseMenu();

        while (!running) {
            try {
                if (System.in.available() > 0) {
                    String command = inputHandler.readCommand();

                    if (inputHandler.isQuit(command)) {
                        renderer.showExitMessage();
                        exit = true;
                        return;
                    }
                    if (inputHandler.isResume(command)) {
                        running = true;
                        renderer.showResumeMessage();
                    } else {
                        renderer.showInvalidCommand();
                        renderer.showPauseMenu();
                    }
                }
            } catch (IOException e) {
                throw new RuntimeException("Ошибка ввода", e);
            }

            try {
                Thread.sleep(PAUSE_SLEEP_TIME);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new RuntimeException("Симуляция прервана", e);
            }
        }
    }

    public void start() {
        for (Action initAction : initActions) {
            initAction.execute(world);
        }
        while (!exit) {
            printGreeting();
            String command = inputHandler.readCommand();

            if (inputHandler.isNextStep(command)) {
                nextTurn();
            } else if (inputHandler.isStart(command) && !exit) {
                startSimulation();
            } else {
                renderer.showInvalidStartCommand();
            }
        }
    }
}