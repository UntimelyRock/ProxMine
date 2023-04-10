package proxMine.VoiceClient;


import org.lwjgl.openal.AL11;
import org.lwjgl.openal.ALC11;
import proxMine.common.Common;


public class AudioRecorder{
    long alcDevice;


    byte[] data;

    public AudioRecorder(){
        //alcDevice = ALC11.alcCaptureOpenDevice("default", 8000, );
    }

    public void open() {
    }

    public byte[] read() {
        return data;
    }


    public void destroy(){
    }

}
