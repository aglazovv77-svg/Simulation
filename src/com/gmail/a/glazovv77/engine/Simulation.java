package com.gmail.a.glazovv77.engine;

import com.gmail.a.glazovv77.input.IInputHandler;
import java.io.IOException;

/*
Класс является главным управляющим компонентом игры.
Отвечает за жизненный цикл симуляции, обработку пользовательского ввода
и управление игровыми ходами (пошаговый режим, бесконечный цикл, пауза).
 */
public class Simulation {

    // Движок, выполняющий игровые действия (ходы существ, обновление состояния)
    private final IActionEngine actionEngine;

    // Обработчик пользовательского ввода (парсинг команд)
    private final IInputHandler inputHandler;

    private boolean running = true;
    private boolean exit = false;

    public Simulation(IActionEngine actionEngine, IInputHandler inputHandler) {
        this.actionEngine = actionEngine;
        this.inputHandler = inputHandler;
    }

    public void printGreeting() {
        System.out.print("""
                Вас приветствует мир Симуляции!\s
                Нажмите [N] для рендеринга одного хода,
                либо [I] для старта бесконечного цикла!\s
                """);
    }


    // Выполняет один игровой ход
    public void nextTurn() {
        actionEngine.turnActions();
        System.out.printf("Счётчик ходов: %d%n", actionEngine.getTurnCount());
    }

    // Запускает бесконечный цикл симуляции
    @SuppressWarnings("BusyWait")
    public void startSimulation() {
        running = true;
        System.out.println("Запуск бесконечной симуляции...");

        for(;;) {
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
                System.out.print("Нажмите [P] для паузы\n");
                System.out.print("Или нажмите [Q] для выхода\n");

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    throw new RuntimeException("Симуляция прервана", e);
                }
            }
        }
    }

    // Приостанавливает бесконечный цикл
    @SuppressWarnings("BusyWait")
    public void pauseSimulation() {
        nextTurn();
        running = false;
        System.out.println("=== ПАУЗА ===");
        System.out.print("Нажмите [R] для продолжения\n");
        System.out.print("Или нажмите [Q] для выхода\n");

        while (!running) {
            try {
                if (System.in.available() > 0) {
                    String command = inputHandler.readCommand();

                    if(inputHandler.isQuit(command)) {
                        System.out.println("Вы вышли из симуляции!");
                        exit = true;
                        return;
                    } else if (inputHandler.isResume(command)) {
                        running = true;
                        System.out.println("Симуляция продолжена!");
                    } else {
                        System.out.print("Неверная команда. Нажмите [R] для продолжения\n");
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

    // Запуск симуляции, точка входа в управление симуляцией
    public void run() {
        actionEngine.initActions();
        for (;;) {
            printGreeting();
            String command = inputHandler.readCommand();

            if (inputHandler.isNextStep(command)) {
                nextTurn();
            } else if (inputHandler.isInfinity(command)) {
                startSimulation();
                if(exit) {
                    return;
                }
            } else {
                System.out.print("Неверная команда! Введите [N] или [I]\n");
            }
        }
    }
}