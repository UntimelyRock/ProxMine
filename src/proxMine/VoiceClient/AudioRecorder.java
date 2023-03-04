package proxMine.VoiceClient;

import proxMine.common.Common;

import javax.sound.sampled.*;

public class AudioRecorder{
    AudioFormat audioFormat;
    DataLine.Info info;
    TargetDataLine targetDataLine;
    byte[] data;

    public AudioRecorder() throws Exception {
        audioFormat = new AudioFormat(Common.AUDIO_SAMPLE_RATE, Common.AUDIO_SAMPLE_SIZE, Common.AUDIO_CHANNELS, Common.AUDIO_SIGNED, Common.AUDIO_BIG_ENDIAN);
        info = new DataLine.Info(TargetDataLine.class, audioFormat);

        if (!AudioSystem.isLineSupported(info)) {
            throw new RuntimeException("Line not supported");
        }
        this.targetDataLine = (TargetDataLine) AudioSystem.getLine(info);
        data = new byte[(int) audioFormat.getSampleRate()];
    }

    public void open() throws Exception {
        targetDataLine.open(audioFormat);
        targetDataLine.start();
    }

    public byte[] read() {
        targetDataLine.read(data, 0, data.length);
        return data;
    }


    public void destroy(){
        targetDataLine.drain();
        targetDataLine.stop();
        targetDataLine.close();
    }

}
