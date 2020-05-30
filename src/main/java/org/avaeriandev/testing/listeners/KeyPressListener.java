package org.avaeriandev.testing.listeners;

import org.avaeriandev.engine.event.EngineEvent;
import org.avaeriandev.engine.event.EngineListener;
import org.avaeriandev.engine.event.events.KeyPressEvent;
import org.avaeriandev.engine.window.Input;
import org.avaeriandev.engine.window.WindowService;
import org.avaeriandev.testing.TestApplication;
import org.avaeriandev.testing.TestEngine;
import org.lwjgl.glfw.GLFW;

import java.awt.*;

public class KeyPressListener implements EngineListener {

    @Override
    public void onEvent(EngineEvent event) {

        KeyPressEvent e = (KeyPressEvent) event;
        TestEngine engine = TestApplication.getEngine();

        Input.addKey(e.getKey());
        System.out.println(Character.toChars(e.getKey()));

        if(e.getKey() == GLFW.GLFW_KEY_ESCAPE) {
            engine.terminate();
        }

        if(e.getKey() == GLFW.GLFW_KEY_F11) {
            WindowService window = engine.getWindow();
            window.setFullscreen(!window.isFullscreen());
        }
    }
}
