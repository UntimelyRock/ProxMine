package proxMine.VoiceClient;

import org.lwjgl.openal.AL11;

public class ProxAudioSource {
    private final int sourceId;

    public ProxAudioSource() {
        this.sourceId = AL11.alGenSources();
    }
    public void setBuffer(int bufferId) {
        stop();
        AL11.alSourcei(sourceId, AL11.AL_BUFFER, bufferId);
    }

    public void setPosition(float x, float y) {
        AL11.alSource3f(sourceId, AL11.AL_POSITION, x, y, 0);
    }

    public void setVelocity(float x, float y) {
        AL11.alSource3f(sourceId, AL11.AL_VELOCITY, x, y, 0);
    }

    public void setGain(float gain) {
        AL11.alSourcef(sourceId, AL11.AL_GAIN, gain);
    }

    public void setProperty(int param, float value) {
        AL11.alSourcef(sourceId, param, value);
    }

    public void play() {
        AL11.alSourcePlay(sourceId);
    }

    public boolean isPlaying() {
        return AL11.alGetSourcei(sourceId, AL11.AL_SOURCE_STATE) == AL11.AL_PLAYING;
    }

    public void pause() {
        AL11.alSourcePause(sourceId);
    }

    public void stop() {
        AL11.alSourceStop(sourceId);
    }

    public void cleanup() {
        stop();
        AL11.alDeleteSources(sourceId);
    }

}
