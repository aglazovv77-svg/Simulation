package com.gmail.a.glazovv77.core.config;

public class GameConfig {

    private GameConfig() {}

    // РАЗМЕРЫ МИРА
    public static final int WORLD_WIDTH = 20;
    public static final int WORLD_HEIGHT = 20;

    // НАЧАЛЬНОЕ КОЛИЧЕСТВО СУЩНОСТЕЙ
    public static final int INITIAL_GRASS = 15;
    public static final int INITIAL_HERBIVORE = 15;
    public static final int INITIAL_PREDATOR = 5;
    public static final int INITIAL_ROCK = 10;
    public static final int INITIAL_TREE = 10;

    // ПОРОГИ ДЛЯ ВОССТАНОВЛЕНИЯ (SPAWN)
    public static final int GRASS_SPAWN_THRESHOLD = 5;
    public static final int HERBIVORE_SPAWN_THRESHOLD = 3;
    // public static final int PREDATOR_SPAWN_THRESHOLD = 2; // на будущее

    // ХАРАКТЕРИСТИКИ СУЩЕСТВ (ИЗ EntityFactory)
    public static final int HERBIVORE_HP = 30;
    public static final int HERBIVORE_SPEED = 1;

    public static final int PREDATOR_HP = 50;
    public static final int PREDATOR_SPEED = 3;
    public static final int PREDATOR_ATTACK = 10;
}
