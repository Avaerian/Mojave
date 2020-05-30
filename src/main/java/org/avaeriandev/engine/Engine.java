package org.avaeriandev.engine;

import org.avaeriandev.engine.audio.AudioService;
import org.avaeriandev.engine.render.RenderService;
import org.avaeriandev.engine.window.WindowService;

public abstract class Engine {

    private AudioService audio;
    private WindowService window;
    private RenderService renderer;

    private boolean isRunning;

    public void initServices() {
        audio = AudioService.getService();
        renderer = RenderService.getService();
        window = WindowService.getService(this);
    }

    public void start() {
        isRunning = true;
        onStart();
    }

    public void terminate() {
        isRunning = false;
        onTerminate();
    }

    public abstract void onStart();
    public abstract void onTerminate();

    public AudioService getAudio() {
        return audio;
    }

    public WindowService getWindow() {
        return window;
    }

    public RenderService getRenderer() {
        return renderer;
    }

    public boolean isRunning() {
        return isRunning;
    }
}
