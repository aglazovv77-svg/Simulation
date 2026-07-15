package com.gmail.a.glazovv77.input;

public interface InputHandler {
    String readCommand();
    boolean isNextStep(String command);
    boolean isStart(String command);
    boolean isPause(String command);
    boolean isResume(String command);
    boolean isQuit(String command);
}
