package proxMine.VoiceClient;

import proxMine.common.Common;

import javax.sound.sampled.*;

public class AudioPlayer {
    private final SourceDataLine sourceDataLine;

    private final AudioFormat audioFormat;

    public AudioPlayer() throws LineUnavailableException {
        audioFormat = new AudioFormat(Common.AUDIO_SAMPLE_RATE, Common.AUDIO_SAMPLE_SIZE, Common.AUDIO_CHANNELS, Common.AUDIO_SIGNED, Common.AUDIO_BIG_ENDIAN);
        sourceDataLine = AudioSystem.getSourceDataLine(audioFormat);
    }

    public void destroy(){
        sourceDataLine.drain();
        sourceDataLine.stop();
        sourceDataLine.close();
    }

    public void open() throws LineUnavailableException {
        sourceDataLine.open(audioFormat);
        sourceDataLine.start();
    }

    public void write(byte[] data) {
        sourceDataLine.write(data, 0, data.length);

    }



}
