package com.gmail.a.glazovv77.pathfinding;

import com.gmail.a.glazovv77.core.entity.Entity;
import com.gmail.a.glazovv77.core.world.Coordinates;
import com.gmail.a.glazovv77.core.world.World;

import java.util.*;

public interface PathFinder {

    List<Coordinates> find(Coordinates start, World world, Class<? extends Entity> targetClass);
}
