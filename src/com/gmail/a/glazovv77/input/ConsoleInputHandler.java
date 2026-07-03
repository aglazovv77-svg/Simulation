package com.gmail.a.glazovv77.input;

import java.util.Scanner;

/*
Класс отвечает за обработку пользовательского ввода
 */
public class ConsoleInputHandler implements IInputHandler {

    private static final String NEXT_STEP = "N";
    private static final String INFINITY = "I";
    private static final String PAUSE = "P";
    private static final String RESUME = "R";
    private static final String QUIT = "Q";

    private final Scanner scanner;

    // Конструктор получает Scanner извне (внедрение зависимости)
    public ConsoleInputHandler() {
        this.scanner = new Scanner(System.in);
    }

    @Override
    public String readCommand() {
        return scanner.nextLine().toUpperCase();
    }

    @Override
    public boolean isNextStep(String command) {
        return NEXT_STEP.equals(command);
    }

    @Override
    public boolean isInfinity(String command) {
        return INFINITY.equals(command);
    }

    @Override
    public boolean isPause(String command) {
        return PAUSE.equals(command);
    }

    @Override
    public boolean isResume(String command) {
        return RESUME.equals(command);
    }

    @Override
    public boolean isQuit(String command) {
        return QUIT.equals(command);
    }
}
