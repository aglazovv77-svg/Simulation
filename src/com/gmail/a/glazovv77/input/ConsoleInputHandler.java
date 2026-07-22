package com.gmail.a.glazovv77.input;

import java.util.Scanner;

public class ConsoleInputHandler implements InputHandler {

    private static final String NEXT_STEP = "N";
    private static final String START = "I";
    private static final String PAUSE = "P";
    private static final String RESUME = "R";
    private static final String QUIT = "Q";

    private final Scanner scanner;

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
    public boolean isStart(String command) {
        return START.equals(command);
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
