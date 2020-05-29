package org.avaeriandev.engine.audio;

import org.lwjgl.openal.AL11;

public class AudioSource {

    private int sourceId;

    public AudioSource(float volume, float pitch) {
        sourceId = AL11.alGenSources();
        AL11.alSourcef(sourceId, AL11.AL_GAIN, volume);
        AL11.alSourcef(sourceId, AL11.AL_PITCH, pitch);
        AL11.alSource3f(sourceId, AL11.AL_POSITION, 0, 0, 0); // TODO
    }

    public void play(int buffer) {
        AL11.alSourcei(sourceId, AL11.AL_BUFFER, buffer);
        AL11.alSourcePlay(sourceId);
    }

    public void stop() {
        AL11.alSourceStop(sourceId);
    }

    public void loop(boolean enable) {
        AL11.alSourcei(sourceId, AL11.AL_LOOPING, enable ? AL11.AL_TRUE : AL11.AL_FALSE);
    }

    public boolean isLooping() {
        return AL11.alGetSourcei(sourceId, AL11.AL_LOOPING) == AL11.AL_TRUE;
    }

    public void delete() {
        AL11.alDeleteSources(sourceId);
    }

    public boolean isPlaying() {
        return AL11.alGetSourcei(sourceId, AL11.AL_PLAYING) == AL11.AL_TRUE;
    }

}
