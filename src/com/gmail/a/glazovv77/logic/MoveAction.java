package com.gmail.a.glazovv77.logic;

import com.gmail.a.glazovv77.core.entity.Creature;
import com.gmail.a.glazovv77.core.entity.Entity;
import com.gmail.a.glazovv77.core.world.Coordinates;
import com.gmail.a.glazovv77.core.world.World;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MoveAction implements Action {

    @Override
    public void execute(World world) {

        List<Creature> creatures = new ArrayList<>();

        for (Map.Entry<Coordinates, Entity> entry : world.getEntries()) {
            Entity entity = entry.getValue();
            if (entity instanceof Creature creature) {
                creatures.add(creature);
            }
        }

        for (Creature creature : creatures) {
            creature.makeMove(world);
        }
    }
}
