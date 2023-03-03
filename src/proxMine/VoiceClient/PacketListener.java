package proxMine.VoiceClient;

import java.net.Socket;

public class PacketListener implements Runnable{
    Socket clientSocket;
    AudioClient parentClient;

    public PacketListener(AudioClient parentClient, Socket clientSocket){
        this.parentClient = parentClient;
        this.clientSocket = clientSocket;
    }

    @Override
    public void run() {

    }
}
