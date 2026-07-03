package com.gmail.a.glazovv77.input;

public interface IInputHandler {
    String readCommand();
    boolean isNextStep(String command);
    boolean isInfinity(String command);
    boolean isPause(String command);
    boolean isResume(String command);
    boolean isQuit(String command);
}
