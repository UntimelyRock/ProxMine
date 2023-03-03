package proxMine.VoiceClient;

import javax.sound.sampled.LineUnavailableException;
import java.net.Socket;

public class PacketWriter implements Runnable{
    AudioRecorder audioRecorder;

    Socket clientSocket;
    AudioClient parentClient;

    public PacketWriter(AudioClient parentClient, Socket clientSocket) throws LineUnavailableException {
        this.parentClient = parentClient;
        this.clientSocket = clientSocket;
        audioRecorder = new AudioRecorder();
    }
    @Override
    public void run() {

    }
}
