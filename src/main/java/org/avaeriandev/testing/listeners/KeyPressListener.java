package org.avaeriandev.testing.listeners;

import org.avaeriandev.engine.event.EngineEvent;
import org.avaeriandev.engine.event.EngineListener;
import org.avaeriandev.engine.event.events.KeyPressEvent;
import org.avaeriandev.engine.window.Input;
import org.avaeriandev.testing.TestApplication;
import org.lwjgl.glfw.GLFW;

public class KeyPressListener implements EngineListener {

    @Override
    public void onEvent(EngineEvent event) {
        KeyPressEvent e = (KeyPressEvent) event;

        System.out.println(Character.toChars(e.getKey()));

        Input.addKey(e.getKey());

        if(e.getKey() == GLFW.GLFW_KEY_ESCAPE) {
            TestApplication.getEngine().terminate();
        }
    }
}
