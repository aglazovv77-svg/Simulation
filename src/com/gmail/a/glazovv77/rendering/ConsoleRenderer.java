package com.gmail.a.glazovv77.rendering;

import com.gmail.a.glazovv77.core.world.Coords;
import com.gmail.a.glazovv77.core.config.GameConfig;
import com.gmail.a.glazovv77.core.entity.Entity;
import com.gmail.a.glazovv77.core.world.World;

import java.util.Map;

/*
Класс отвечает за отрисовку мира в консоли
 */
public class ConsoleRenderer implements IRenderer {

    private final World world;

    public ConsoleRenderer(World world) {
        this.world = world;
    }

    // Отрисовывает текущее состояние мира в консоли
    @Override
    public void render() {
        // Очищаем консоль правильно
        System.out.print("\033[2J\033[H");  // Очистить весь экран и вернуть курсор
        System.out.flush();

        // Получение подсвеченных клеток
        Map<Coords, String> highlights = world.getHighlightedCells();

        for (int row = GameConfig.WORLD_HEIGHT; row >= 1; row--) {
            StringBuilder line = new StringBuilder();

            for (int col = GameConfig.WORLD_WIDTH; col >= 1; col--) {
                Coords coords = new Coords(row, col);

                String cellSprite = world.isCellEmpty(coords)
                        ? "⬛"
                        : getEntitySprite(world.getEntity(coords));

                // Проверяем подсветку и применяем цвет
                if (highlights.containsKey(coords)) {
                    String type = highlights.get(coords);

                    cellSprite = switch (type) {
                        case "attack" -> "\u001B[41m%s\u001B[0m".formatted(cellSprite); // Красный фон
                        case "eat" -> "\u001B[42m%s\u001B[0m".formatted(cellSprite);    // Зеленый фон
                        default -> cellSprite;
                    };
                }
                line.append(cellSprite);
            }
            System.out.println(line);
        }
        System.out.println("----------------------------------------------------------");


        // ОЧИЩАЕМ ПОДСВЕТКУ ПОСЛЕ ОТРИСОВКИ
        world.clearHighlights();
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
}
