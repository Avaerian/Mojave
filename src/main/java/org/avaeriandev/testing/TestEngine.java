package org.avaeriandev.testing;

import org.avaeriandev.engine.Engine;
import org.avaeriandev.engine.event.EventManager;
import org.avaeriandev.engine.event.events.KeyPressEvent;
import org.avaeriandev.engine.event.events.KeyReleaseEvent;
import org.avaeriandev.engine.window.Input;
import org.avaeriandev.engine.window.WindowService;
import org.avaeriandev.testing.listeners.KeyPressListener;
import org.avaeriandev.testing.listeners.KeyReleaseListener;
import org.lwjgl.glfw.GLFW;

public class TestEngine extends Engine {

    private TestEngine() {}
    private static TestEngine instance;
    public static TestEngine get() {
        if(instance == null) instance = new TestEngine();
        return instance;
    }

    public void createWindow() {
        WindowService window = super.getWindow();
        if(window == null) throw new IllegalStateException("Window service is unavailable");

        // Configure
        window.setFlag(GLFW.GLFW_VISIBLE, false);
        window.setFlag(GLFW.GLFW_RESIZABLE, true);
        window.setFlag(GLFW.GLFW_FOCUSED, true);

        // Register Events
        EventManager.registerEvent(KeyPressEvent.class);
        EventManager.registerEvent(KeyReleaseEvent.class);
        EventManager.registerListener(KeyPressEvent.class, new KeyPressListener());
        EventManager.registerListener(KeyReleaseEvent.class, new KeyReleaseListener());

        // Create window
        window.create(1270, 720, "Mojave Game Engine");
    }

    @Override
    public void onStart() {
        getAudio().start();
        getRenderer().start();
        getWindow().start();
    }

    @Override
    public void onTerminate() {
        getAudio().stop();
        getRenderer().stop();
        getWindow().stop();
    }
}
