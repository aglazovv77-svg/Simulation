package com.gmail.a.glazovv77.rendering;

import com.gmail.a.glazovv77.core.world.Coordinates;
import com.gmail.a.glazovv77.core.entity.Entity;
import com.gmail.a.glazovv77.core.world.World;

public class ConsoleRenderer implements Renderer {

    private static final String NEXT_STEP = "N";
    private static final String START = "I";
    private static final String PAUSE = "P";
    public static final String RESUME = "R";
    private static final String QUIT = "Q";

    private static final String CLEAR_SCREEN = "\033[2J\033[H";
    private final World world;

    public ConsoleRenderer(World world) {
        this.world = world;
    }

    @Override
    public void render() {

        int rowCount = world.getRowCount();
        int columnCount = world.getColumnCount();

        System.out.print(CLEAR_SCREEN);
        System.out.flush();

        for (int row = rowCount; row >= 1; row--) {
            StringBuilder line = new StringBuilder();

            for (int col = columnCount; col >= 1; col--) {
                Coordinates coordinates = new Coordinates(row, col);

                String cellSprite = world.isCellEmpty(coordinates) ? "⬛" : getEntitySprite(world.getEntity(coordinates));

                line.append(cellSprite);
            }
            System.out.println(line);
        }
        System.out.println("----------------------------------------------------------");

    }

    // Возвращает Unicode-символ для конкретного типа сущности
    private String selectUnicodeSpriteForEntity(Entity entity) {
        return switch ((entity.getClass().getSimpleName())) {
            case "Herbivore" -> "🐇";
            case "Predator" -> "🐺";
            case "Grass" -> "🌿";
            case "Rock" -> "🪨";
            case "Tree" -> "🌲";
            default -> "";
        };
    }

    // Обёртка для получения спрайта сущности
    private String getEntitySprite(Entity entity) {
        return selectUnicodeSpriteForEntity(entity);
    }

    @Override
    public void showGreeting() {
        System.out.printf("""
                Вас приветствует мир Симуляции!\s
                Нажмите [%s] для рендеринга одного хода,
                либо [%s] для старта бесконечного цикла!\s
                """, NEXT_STEP, START);
    }

    @Override
    public void showPauseMenu() {
        System.out.println("=== ПАУЗА ===");
        System.out.printf("Нажмите [%s] для продолжения\n", RESUME);
        System.out.printf("Или нажмите [%s] для выхода\n", QUIT);
    }

    @Override
    public void showResumeMessage() {
        System.out.println("Симуляция продолжена!");
    }

    @Override
    public void showInvalidCommand() {
        System.out.println("Неверная команда!!!");
    }

    @Override
    public void showExitMessage() {
        System.out.println("Вы вышли из симуляции!");
    }

    @Override
    public void showPausePrompt() {
        System.out.printf("Нажмите [%s] для паузы\n", PAUSE);
        System.out.printf("Или нажмите [%s] для выхода\n", QUIT);
    }

    @Override
    public void showTurnCount(int turnCount) {
        System.out.printf("Счётчик ходов: %d%n", turnCount);
    }

    @Override
    public void showStartSimulationMessage() {
        System.out.println("Запуск бесконечной симуляции...");
    }

    @Override
    public void showInvalidStartCommand() {
        System.out.printf("Неверная команда! Введите [%s] или [%s]\n", NEXT_STEP, START);
    }
}
