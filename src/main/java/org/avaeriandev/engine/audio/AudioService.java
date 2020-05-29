package org.avaeriandev.engine.audio;

import com.sun.media.sound.WaveFileReader;
import org.avaeriandev.engine.EngineService;
import org.lwjgl.BufferUtils;
import org.lwjgl.openal.*;
import org.lwjgl.stb.STBVorbis;
import org.lwjgl.stb.STBVorbisInfo;
import org.lwjgl.system.MemoryUtil;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AudioService extends EngineService {

    private long device;
    private long context;
    private ALCCapabilities deviceCaps;

    private List<Integer> buffers;
    private List<AudioSource> sources;

    private AudioService() {
        init();
        buffers = new ArrayList<>();
        sources = new ArrayList<>();
    }
    private static AudioService instance = null;

    public static AudioService getService() {
        if(instance == null) {
            instance = new AudioService();
        }
        return instance.isEnabled() ? instance : null;
    }

    @Override
    public void init() {

        // Open default device
        device = ALC11.alcOpenDevice((ByteBuffer) null);
        if(device == MemoryUtil.NULL) {
            markServiceAsDisabled("Default audio device was unable to be opened");
            return;
        }

        // Create device capabilities
        deviceCaps = ALC.createCapabilities(device);
        if(!deviceCaps.OpenALC11) {
            markServiceAsDisabled("OpenALC11 unsupported");
            return;
        }

        // Create OpenAL context
        context = ALC11.alcCreateContext(device, (IntBuffer) null);
        if(context == MemoryUtil.NULL) {
            markServiceAsDisabled("ALC context was unable to be created");
            return;
        }

        // Setup rest of OpenAL
        ALC11.alcMakeContextCurrent(context);
        AL.createCapabilities(deviceCaps);

        // Enable service for engine
        markServiceAsEnabled();
    }

    public void start() {
        AL11.alListener3f(AL11.AL_POSITION, 0, 0, 0);
        AL11.alListener3f(AL11.AL_VELOCITY, 0, 0, 0);
        AL11.alListeneri(AL11.AL_GAIN, 1);
    }

    public int loadSound(String file) throws IOException, UnsupportedAudioFileException {

        // Get file and high-level data
        File soundFile = new File(file);
        AudioInputStream soundStream = AudioSystem.getAudioInputStream(soundFile);
        AudioFormat soundData = soundStream.getFormat();

        // Create buffer
        byte[] soundByteArray = new byte[soundStream.available()];
        soundStream.read(soundByteArray);
        ByteBuffer soundBuffer = BufferUtils.createByteBuffer(soundByteArray.length).put(soundByteArray);
        soundBuffer.rewind();

        // Prepare for OpenAL
        int buffer = AL11.alGenBuffers();
        buffers.add(buffer);
        AL11.alBufferData(buffer, getFormat(soundData), soundBuffer, (int) soundData.getSampleRate());

        // Finish
        soundStream.close();
        soundBuffer.flip();
        return buffer;
    }

    private int getFormat(AudioFormat soundData) {
        if(soundData.getChannels() == 1) {
            return soundData.getSampleSizeInBits() == 8 ? AL11.AL_FORMAT_MONO8 : AL11.AL_FORMAT_MONO16;
        } else if(soundData.getChannels() == 2) {
            return soundData.getSampleSizeInBits() == 8 ? AL11.AL_FORMAT_STEREO8 : AL11.AL_FORMAT_STEREO16;
        }
        return -1;
    }

    public void stop() {
        buffers.forEach(buffer -> AL11.alDeleteBuffers(buffer));
        sources.forEach(source -> source.delete());
        ALC11.alcCloseDevice(device);
        ALC.destroy();
    }
}
