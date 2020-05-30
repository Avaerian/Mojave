package org.avaeriandev.engine.render;

import org.avaeriandev.engine.EngineService;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

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

    public void prepare() {
        //GL11.glClearColor(1, 0, 0, 1);
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
    }

    public void render(RawModel rawModel) {
        // Prepare
        GL30.glBindVertexArray(rawModel.getVaoID());
        GL20.glEnableVertexAttribArray(0);

        // Draw
        GL11.glDrawArrays(GL11.GL_TRIANGLES, 0, rawModel.getVertexCount());

        // Clean up
        GL20.glDisableVertexAttribArray(0);
        GL30.glBindVertexArray(0);
    }

    public void stop() {

    }

}
