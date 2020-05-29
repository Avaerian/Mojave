package org.avaeriandev.engine.event.events;

import org.avaeriandev.engine.event.EngineEvent;

public class KeyEvent extends EngineEvent {

    private int key, scancode, action, mods;

    public KeyEvent(int key, int scancode, int action, int mods) {
        this.key = key;
        this.scancode = scancode;
        this.action = action;
        this.mods = mods;
    }

    public int getKey() {
        return key;
    }

    public int getScancode() {
        return scancode;
    }

    public int getAction() {
        return action;
    }

    public int getMods() {
        return mods;
    }
}
