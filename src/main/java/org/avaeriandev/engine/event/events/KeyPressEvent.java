package org.avaeriandev.engine.event.events;

public class KeyPressEvent extends KeyEvent {
    public KeyPressEvent(int key, int scancode, int action, int mods) {
        super(key, scancode, action, mods);
    }
}
