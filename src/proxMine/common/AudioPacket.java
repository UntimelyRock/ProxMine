package proxMine.common;

import java.io.*;

public class AudioPacket implements Serializable {
    public int playerID;
    public byte[] audioData;

    public AudioPacket(int playerID, byte[] audioData){
        this.playerID = playerID;
        this.audioData = audioData;
    }
}
