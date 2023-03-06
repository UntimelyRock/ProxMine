package proxMine.VoiceClient;

import org.lwjgl.openal.AL11;
import org.lwjgl.openal.ALUtil;

import java.nio.ByteBuffer;

public class AudioBuffer {
    private final int bufferID;

    public AudioBuffer(byte[] buffer) throws Exception {
        this.bufferID = AL11.alGenBuffers();

        //AL11.alBufferData(bufferID, );

    }

    public int getBufferId() {
        return this.bufferID;
    }

    public void cleanup() {
        AL11.alDeleteBuffers(this.bufferID);
    }

}
