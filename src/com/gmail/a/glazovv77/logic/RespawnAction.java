package com.gmail.a.glazovv77.logic;

import com.gmail.a.glazovv77.core.world.World;

public class RespawnAction implements Action {

    private final RespawnManager respawnManager;

    public RespawnAction(RespawnManager respawnManager) {
        this.respawnManager = respawnManager;
    }

    @Override
    public void execute(World world) {
        respawnManager.respawnEntities();
    }
}
