package proxMine.VoiceClient;

import javax.sound.sampled.LineUnavailableException;
import java.io.IOException;
import java.net.Socket;

public class AudioClient {
    Socket clientSocket;
    PacketWriter packetWriter;
    PacketListener packetListener;
    public AudioClient(){
    }


    public void connect(String host, int port) throws LineUnavailableException, IOException {
        clientSocket = new Socket(host, port);
        packetWriter = new PacketWriter(this, clientSocket);
        packetListener = new PacketListener(this, clientSocket);
    }


}
