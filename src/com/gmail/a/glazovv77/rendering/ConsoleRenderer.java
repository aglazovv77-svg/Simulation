package com.gmail.a.glazovv77.rendering;

import com.gmail.a.glazovv77.core.world.Coordinates;
import com.gmail.a.glazovv77.core.entity.Entity;
import com.gmail.a.glazovv77.core.world.World;

public class ConsoleRenderer implements Renderer {

    private final World world;

    public ConsoleRenderer(World world) {
        this.world = world;
    }

    @Override
    public void render() {

        int rowCount = world.getRowCount();
        int columnCount = world.getColumnCount();

        System.out.print("\033[2J\033[H");  // Очистить весь экран и вернуть курсор
        System.out.flush();

        for (int row = rowCount; row >= 1; row--) {
            StringBuilder line = new StringBuilder();

            for (int col = columnCount; col >= 1; col--) {
                Coordinates coordinates = new Coordinates(row, col);

                String cellSprite = world.isCellEmpty(coordinates)
                        ? "⬛"
                        : getEntitySprite(world.getEntity(coordinates));

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
}
