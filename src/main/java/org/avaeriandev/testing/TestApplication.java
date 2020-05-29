package org.avaeriandev.testing;

import org.avaeriandev.engine.audio.AudioSource;

import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;

public class TestApplication {

    private static final TestEngine engine = TestEngine.get();

    public static void main(String[] args) throws IOException, UnsupportedAudioFileException {

        engine.initServices();
        engine.prepareWindow();
        engine.start();
    }

    public static TestEngine getEngine() {
        return engine;
    }

}
