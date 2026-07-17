package com.gmail.a.glazovv77.rendering;

public interface Renderer {

    void render();

    void showGreeting();

    void showTurnCount(int turnCount);

    void showStartSimulationMessage();

    void showPausePrompt();

    void showPauseMenu();

    void showResumeMessage();

    void showInvalidCommand();

    void showExitMessage();

    void showInvalidStartCommand();
}
