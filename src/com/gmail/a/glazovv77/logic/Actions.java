package com.gmail.a.glazovv77.logic;

import com.gmail.a.glazovv77.core.entity.*;
import com.gmail.a.glazovv77.core.world.Coords;
import com.gmail.a.glazovv77.engine.*;
import com.gmail.a.glazovv77.factory.EntityFactory;
import com.gmail.a.glazovv77.rendering.*;
import com.gmail.a.glazovv77.core.world.World;

import java.util.*;

/*
Класс центральный оркестратор игровой логики.
Координирует все действия, происходящие в симуляции:
атаки, перемещения, разрешение конфликтов, респавн существ и рендеринг
 */
public class Actions implements IActionEngine {

    private final World world;
    private final IRenderer renderer;
    private final EntityFactory entityFactory;

    private final AttackHandler attackHandler;
    private final MovementHandler movementHandler;
    private final ConflictResolver conflictResolver;
    private final RespawnManager respawnManager;

    private int turnCount = 0;

    public Actions(World world,
                   IRenderer renderer,
                   EntityFactory entityFactory,
                   AttackHandler attackHandler,
                   MovementHandler movementHandler,
                   ConflictResolver conflictResolver,
                   RespawnManager respawnManager) {
        this.world = world;
        this.renderer = renderer;
        this.entityFactory = entityFactory;
        this.attackHandler = attackHandler;
        this.movementHandler = movementHandler;
        this.conflictResolver = conflictResolver;
        this.respawnManager = respawnManager;
    }

    // Инициализация игрового мира перед началом симуляции
    @Override
    public void initActions() {

        world.setupEntitiesPosition(entityFactory);
        renderer.render();
    }

    // Выполняет один полный игровой ход
    @Override
    public void turnActions() {

        // Логика атак
        AttackResult attackResult = attackHandler.processAttacks();

        // Удаляем всех убитых травоядных
        for (Coords deadCell : attackResult.getDeadHerbivores()) {
            world.removeEntity(deadCell);
        }

        // логика перемещений
        List<Move> moves = movementHandler.collectMoves(attackResult);

        // разрешение конфликтов
        List<Move> resolvedMoves = conflictResolver.resolveConflicts(moves);

        // Выполняем разрешённые ходы
        for (var move : resolvedMoves) {
            world.moveEntity(move.from(), move.to());
        }

        // Рисуем мир в консоли
        renderer.render();

        // восстановление существ(травы и травоядных)
        respawnManager.respawnEntities();

        turnCount++;

        // Сбрасываем флаг атакованных травоядных
        for (var entry : world.getEntries()) {
            var entity = entry.getValue();
            if (entity instanceof Herbivore herbivore) {
                herbivore.wasAttackedThisTurn = false;
            }
        }
    }

    // Количество итераций мира
    @Override
    public int getTurnCount() {
        return turnCount;
    }
}