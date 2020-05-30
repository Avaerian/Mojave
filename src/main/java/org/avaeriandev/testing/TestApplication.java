package org.avaeriandev.testing;

import org.avaeriandev.engine.audio.AudioSource;
import org.avaeriandev.engine.render.Loader;
import org.avaeriandev.engine.render.RawModel;
import org.avaeriandev.engine.render.RenderService;
import org.avaeriandev.engine.window.Input;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL11;

public class TestApplication {

    private static final TestEngine engine = TestEngine.get();

    public static void main(String[] args) {

        // Initial startup
        engine.initServices();
        engine.createWindow();
        engine.start();

        int buffer = engine.getAudio().loadSound("C:/Users/rblxa/Desktop/Easy Lemon.wav");
        if(buffer != -1) {
            AudioSource source = new AudioSource(1, 1);
            source.play(buffer);
            source.setLooping(!source.isLooping());
        }

        // TODO: test code
        Loader loader = new Loader();
        RenderService renderer = engine.getRenderer();

        float[] vertices = {
                // Bottom-left triangle
                -0.5f, 0.5f, 0f,
                -0.5f, -0.5f, 0f,
                0.5f, -0.5f, 0f,

                // Top-right triangle
                0.5f, -0.5f, 0f,
                0.5f, 0.5f, 0f,
                -0.5f, 0.5f, 0f
        };

        RawModel rawModel = loader.loadToVAO(vertices);

        // Run every frame while engine is running
        while(engine.getWindow().isRunning()) {

            renderer.prepare();

            if(Input.isKeyDown(GLFW.GLFW_KEY_S)) {
                GL11.glClearColor(0, 1, 0, 0);
            } else {
                GL11.glClearColor(1,0,0,0);
            }

            renderer.render(rawModel);

            engine.getWindow().prepare();
        }

        loader.terminate();

        // Clean up
        if(engine.isRunning()) engine.terminate();
        System.out.println("Engine has stopped");
    }

    public static TestEngine getEngine() {
        return engine;
    }

}
