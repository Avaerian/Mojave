package org.avaeriandev.engine.event.events;

public class KeyReleaseEvent extends KeyEvent {
    public KeyReleaseEvent(int key, int scancode, int action, int mods) {
        super(key, scancode, action, mods);
    }
}
