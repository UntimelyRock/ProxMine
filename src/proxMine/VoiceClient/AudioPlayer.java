package proxMine.VoiceClient;

import proxMine.common.Common;

import javax.sound.sampled.*;

public class AudioPlayer {
    private final SourceDataLine sourceDataLine;

    private final AudioFormat audioFormat;

    private final AudioClient parentClient;

    public AudioPlayer(AudioClient parentClient) throws LineUnavailableException {
        this.parentClient = parentClient;
        audioFormat = new AudioFormat(Common.AUDIO_SAMPLE_RATE, Common.AUDIO_SAMPLE_SIZE, Common.AUDIO_CHANNELS, Common.AUDIO_SIGNED, Common.AUDIO_BIG_ENDIAN);
        sourceDataLine = AudioSystem.getSourceDataLine(audioFormat);

    }



}
