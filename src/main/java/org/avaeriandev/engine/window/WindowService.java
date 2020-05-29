package org.avaeriandev.engine.window;

import org.avaeriandev.engine.Engine;
import org.avaeriandev.engine.EngineService;
import org.avaeriandev.engine.audio.AudioSource;
import org.avaeriandev.engine.event.EventManager;
import org.avaeriandev.engine.event.events.KeyPressEvent;
import org.avaeriandev.engine.event.events.KeyReleaseEvent;
import org.lwjgl.glfw.Callbacks;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;
import org.lwjgl.system.MemoryUtil;

import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;

public class WindowService extends EngineService {

    private long window;
    private Engine engine;

    private WindowService(Engine engine) {
        init();
        this.engine = engine;
    }
    private static WindowService instance = null;
    public static WindowService getService(Engine engine) {
        if(instance == null) {
            instance = new WindowService(engine);
        }
        return instance.isEnabled() ? instance : null;
    }

    @Override
    protected void init() {
        GLFWErrorCallback.createPrint(System.err).set();

        if(!GLFW.glfwInit()) {
            markServiceAsDisabled("Unable to initialize GLFW");
        } else {
            markServiceAsEnabled();
        }
    }

    public void setFlag(int flag, boolean state) {
        int glfwState = state ? GLFW.GLFW_TRUE : GLFW.GLFW_FALSE;
        GLFW.glfwWindowHint(flag, glfwState);
    }

    public void create(int width, int height, String title) {

        window = GLFW.glfwCreateWindow(width, height, title, MemoryUtil.NULL, MemoryUtil.NULL);
        if(window == MemoryUtil.NULL) throw new RuntimeException("Failed to create GLFW window");

        // Setup keyboard event
        GLFW.glfwSetKeyCallback(window, (window, key, scancode, action, mods) -> {
            switch(action) {
                case GLFW.GLFW_PRESS:
                    EventManager.callEvent(new KeyPressEvent(key, scancode, action, mods));
                    break;
                case GLFW.GLFW_RELEASE:
                    EventManager.callEvent(new KeyReleaseEvent(key, scancode, action, mods));
                    break;
            }
        });

        // Setup context for OpenGL
        GLFW.glfwMakeContextCurrent(window);
        GLFW.glfwSwapInterval(1); // enables vsync
    }

    public void start() {
        GLFW.glfwShowWindow(window);

        // TODO: Get OpenGL ready
        GL.createCapabilities();
        GL11.glClearColor(1,0,0,0);

        int buffer;
        try {
            buffer = engine.getAudio().loadSound("C:/Users/rblxa/Desktop/Easy Lemon.wav");
        } catch (IOException | UnsupportedAudioFileException e) {
            e.printStackTrace();
            return;
        }

        AudioSource source = new AudioSource(1, 1);
        source.play(buffer);

        while(isRunning()) {

            engine.logicLoop();
            if(source.isPlaying()) {
                System.out.println("Sound is being played!");
            }

            GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
            GLFW.glfwSwapBuffers(window);
            GLFW.glfwWaitEvents();
        }

        destroy();
    }

    public boolean isRunning() {
        return !GLFW.glfwWindowShouldClose(window);
    }

    public void close() {
        GLFW.glfwSetWindowShouldClose(window, true);
    }

    private void destroy() {

        Callbacks.glfwFreeCallbacks(window);
        GLFW.glfwDestroyWindow(window);
        GLFW.glfwTerminate();
        GLFW.glfwSetErrorCallback(null).free();

    }
}
