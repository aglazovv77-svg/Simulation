package com.gmail.a.glazovv77.logic;

import com.gmail.a.glazovv77.core.world.Coords;

/*
Класс record, служит контейнером для данных о перемещении сущности с одной клетки на другую
 */
public record Move(Coords from, Coords to) {
}
