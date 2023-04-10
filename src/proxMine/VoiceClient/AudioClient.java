package proxMine.VoiceClient;

import java.io.IOException;
import java.net.Socket;

public class AudioClient {


    Socket clientSocket;
    PacketWriter packetWriter;
    PacketListener packetListener;




    public AudioClient(){

    }


    public void connect(String host, int port) throws Exception {
        clientSocket = new Socket(host, port);
        //packetWriter = new PacketWriter(this, clientSocket);
        packetListener = new PacketListener(this, clientSocket);

    }

    public void disconnect() throws IOException {
        destroy();
    }

    public void destroy() throws IOException {
        clientSocket.close();
        packetListener.destroy();
        packetWriter.destroy();
    }

    public void start(){
        packetWriter.start();
        packetListener.start();
    }


}
