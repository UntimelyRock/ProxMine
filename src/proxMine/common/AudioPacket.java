package proxMine.common;

import java.io.Serializable;

public class AudioPacket implements Serializable {
    long playerID;
    byte[] audioData;

    AudioPacket(long playerID, byte[] audioData){
        this.playerID = playerID;
        this.audioData = audioData;
    }
}
