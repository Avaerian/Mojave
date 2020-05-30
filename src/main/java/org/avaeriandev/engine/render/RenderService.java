package org.avaeriandev.engine.render;

import org.avaeriandev.engine.EngineService;
import org.lwjgl.opengl.GL;

public class RenderService extends EngineService {

    private static RenderService instance = null;

    private RenderService() {
        init();
    }

    public static RenderService getService() {
        if(instance == null) instance = new RenderService();
        return instance.isEnabled() ? instance : null;
    }

    @Override
    protected void init() {
        markServiceAsEnabled();
    }

    public void start() {
        GL.createCapabilities();
    }

    public void stop() {

    }

}
