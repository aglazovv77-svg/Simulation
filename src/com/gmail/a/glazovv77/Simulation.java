package com.gmail.a.glazovv77;

import com.gmail.a.glazovv77.core.world.World;
import com.gmail.a.glazovv77.input.InputHandler;
import com.gmail.a.glazovv77.logic.Action;

import java.io.IOException;
import java.util.List;

public class Simulation {

    private static final String NEXT_STEP = "N";
    private static final String START = "I";
    private static final String PAUSE = "P";
    private static final String RESUME = "R";
    private static final String QUIT = "Q";

    private final World world;
    private final InputHandler inputHandler;
    private final List<Action> initActions;
    private final List<Action> turnActions;

    private int turnCount = 0;
    private boolean running = true;
    private boolean exit = false;

    public Simulation(World world, InputHandler inputHandler, List<Action> initActions, List<Action> turnActions) {
        this.world = world;
        this.inputHandler = inputHandler;
        this.initActions = initActions;
        this.turnActions = turnActions;
    }

    public void printGreeting() {
        System.out.printf("""
                Вас приветствует мир Симуляции!\s
                Нажмите [%s] для рендеринга одного хода,
                либо [%s] для старта бесконечного цикла!\s
                """, NEXT_STEP, START);
    }


    public void nextTurn() {
       for(Action turnAction : turnActions) {
           turnAction.execute(world);
       }
        System.out.printf("Счётчик ходов: %d%n", turnCount);
       turnCount++;
    }

    @SuppressWarnings("BusyWait")
    public void startSimulation() {
        running = true;
        System.out.println("Запуск бесконечной симуляции...");

        while (true){
            try {
                if (System.in.available() > 0) {
                    String command = inputHandler.readCommand();

                    if (inputHandler.isPause(command)) {
                        pauseSimulation();
                        if(exit) {
                            return;
                        }
                    }

                    if(inputHandler.isQuit(command)) {
                        System.out.println("Вы вышли из симуляции!");
                        exit = true;
                        return;
                    }
                }
            } catch (IOException e) {
                throw new RuntimeException("Ошибка ввода", e);
            }

            if (running) {
                nextTurn();
                System.out.printf("Нажмите [%s] для паузы\n", PAUSE);
                System.out.printf("Или нажмите [%s] для выхода\n", QUIT);

                try {
                    Thread.sleep(1000);
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
        System.out.println("=== ПАУЗА ===");
        System.out.printf("Нажмите [%s] для продолжения\n", RESUME);
        System.out.printf("Или нажмите [%s] для выхода\n", QUIT);

        while (!running) {
            try {
                if (System.in.available() > 0) {
                    String command = inputHandler.readCommand();

                    if(inputHandler.isQuit(command)) {
                        System.out.println("Вы вышли из симуляции!");
                        exit = true;
                        return;
                    } if (inputHandler.isResume(command)) {
                        running = true;
                        System.out.println("Симуляция продолжена!");
                    } else {
                        System.out.printf("Неверная команда. Нажмите [%s] для продолжения\n", RESUME);
                    }
                }
            } catch (IOException e) {
                throw new RuntimeException("Ошибка ввода", e);
            }

            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new RuntimeException("Симуляция прервана", e);
            }
        }
    }

    public void start() {
       for(Action initAction : initActions) {
           initAction.execute(world);
       }
        while (true) {
            printGreeting();
            String command = inputHandler.readCommand();

            if (inputHandler.isNextStep(command)) {
                nextTurn();
            } else if (inputHandler.isStart(command)) {
                startSimulation();
                if(exit) {
                    return;
                }
            } else {
                System.out.printf("Неверная команда! Введите [%s] или [%s]\n", NEXT_STEP, START);
            }
        }
    }
}