package org.avaeriandev.engine;

import org.avaeriandev.engine.audio.AudioService;
import org.avaeriandev.engine.window.WindowService;

public abstract class Engine {

    private AudioService audio;
    private WindowService window;

    private boolean isRunning;

    public void initServices() {
        audio = AudioService.getService();
        window = WindowService.getService(this);
    }

    public abstract void logicLoop();
    public abstract void start();
    public abstract void stop();

    public AudioService getAudio() {
        return audio;
    }

    public WindowService getWindow() {
        return window;
    }

}
