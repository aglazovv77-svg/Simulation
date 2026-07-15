package com.gmail.a.glazovv77.logic;

import com.gmail.a.glazovv77.core.world.World;
import com.gmail.a.glazovv77.rendering.Renderer;

public class RenderAction implements Action {

    private final Renderer renderer;

    public RenderAction(Renderer renderer) {
        this.renderer = renderer;
    }

    @Override
    public void execute(World world) {
        renderer.render();
    }
}
