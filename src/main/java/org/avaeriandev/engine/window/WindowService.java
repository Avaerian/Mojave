package org.avaeriandev.engine.window;

import org.avaeriandev.engine.Engine;
import org.avaeriandev.engine.EngineService;
import org.avaeriandev.engine.event.EventManager;
import org.avaeriandev.engine.event.events.KeyPressEvent;
import org.avaeriandev.engine.event.events.KeyReleaseEvent;
import org.lwjgl.BufferUtils;
import org.lwjgl.glfw.Callbacks;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.system.MemoryUtil;

import java.nio.Buffer;
import java.nio.IntBuffer;

public class WindowService extends EngineService {

    private long window;
    private Engine engine;
    private boolean isActive;
    private boolean isFullscreen;

    private int previousX, previousY;
    private int previousWidth, previousHeight;

    private WindowService(Engine engine) {
        init();
        this.engine = engine;
        isActive = false;
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

        // Setup resize callback
        GLFW.glfwSetWindowSizeCallback(window, (window, newWidth, newHeight) -> {
            GL11.glViewport(0, 0, newWidth, newHeight);
        });

        // Setup context for OpenGL
        GLFW.glfwMakeContextCurrent(window);
        GLFW.glfwSwapInterval(1); // enables vsync
    }

    public void start() {
        GLFW.glfwShowWindow(window);
        isActive = true;
    }

    public boolean isRunning() {
        return !GLFW.glfwWindowShouldClose(window) && isActive;
    }

    @Override
    public void stop() {
        System.out.println("Window has been requested to stop.");
        GLFW.glfwSetWindowShouldClose(window, true);
        isActive = false;
        destroy();
    }

    // TODO: temporary function
    public void prepare() {
        GLFW.glfwSwapBuffers(window);
        GLFW.glfwPollEvents();
    }

    private void destroy() {

        System.out.println("Window has been requested to be destroyed.");
        Callbacks.glfwFreeCallbacks(window);
        GLFW.glfwDestroyWindow(window);
        GLFW.glfwTerminate();
        GLFW.glfwSetErrorCallback(null).free();
        System.out.println("Window has been destroyed.");

    }

    public void setFullscreen(boolean isFullscreen) {
        this.isFullscreen = isFullscreen;

        if(isFullscreen) {
            saveWindowData();
            GLFWVidMode mode = GLFW.glfwGetVideoMode(GLFW.glfwGetPrimaryMonitor());
            GLFW.glfwSetWindowMonitor(window, GLFW.glfwGetWindowMonitor(window), 0, 0, mode.width(), mode.height(), GLFW.GLFW_DONT_CARE);
        } else {
            GLFW.glfwSetWindowMonitor(window, MemoryUtil.NULL, previousX, previousY, previousWidth, previousHeight, GLFW.GLFW_DONT_CARE);
        }
    }

    private void saveWindowData() {

        IntBuffer x = BufferUtils.createIntBuffer(1);
        IntBuffer y = BufferUtils.createIntBuffer(1);
        IntBuffer width = BufferUtils.createIntBuffer(1);
        IntBuffer height = BufferUtils.createIntBuffer(1);

        GLFW.glfwGetWindowPos(window, x, y);
        GLFW.glfwGetWindowSize(window, width, height);

        previousX = x.get();
        previousY = y.get();
        previousWidth = width.get();
        previousHeight = height.get();
    }

    public boolean isFullscreen() {
        return isFullscreen;
    }
}
