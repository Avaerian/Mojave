package org.avaeriandev.engine;

public abstract class EngineService {

    private boolean isEnabled = false;

    protected abstract void init();

    protected void markServiceAsDisabled(String error) {
        this.isEnabled = false;
        throw new IllegalStateException(error != null ? error : "Service is unavailable");
    }

    protected void markServiceAsEnabled() {
        this.isEnabled = true;
    }

    public boolean isEnabled() {
        return isEnabled;
    }

    public abstract void start();
    public abstract void stop();
}
