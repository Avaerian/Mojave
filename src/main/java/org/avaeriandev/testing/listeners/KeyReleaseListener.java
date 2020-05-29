package org.avaeriandev.testing.listeners;

import org.avaeriandev.engine.event.EngineEvent;
import org.avaeriandev.engine.event.EngineListener;
import org.avaeriandev.engine.event.events.KeyReleaseEvent;
import org.avaeriandev.engine.window.Input;
import org.lwjgl.glfw.GLFW;

public class KeyReleaseListener implements EngineListener {
    @Override
    public void onEvent(EngineEvent event) {
        KeyReleaseEvent e = (KeyReleaseEvent) event;

        Input.removeKey(e.getKey());

        System.out.println(Character.toChars(e.getKey()));
    }
}
