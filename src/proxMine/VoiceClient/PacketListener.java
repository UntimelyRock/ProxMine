package proxMine.VoiceClient;

import arc.util.Log;
import mindustry.Vars;
import mindustry.ui.dialogs.BaseDialog;
import proxMine.common.AudioPacket;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

public class PacketListener extends Thread {
    Socket clientSocket;
    AudioClient parentClient;
    AudioPlayer audioPlayer;

    public PacketListener(AudioClient parentClient, Socket clientSocket) throws Exception {
        this.parentClient = parentClient;
        this.clientSocket = clientSocket;
        audioPlayer = new AudioPlayer();
    }

    public void destroy(){
        audioPlayer.destroy();
        if(!Thread.currentThread().isInterrupted()){
            Thread.currentThread().interrupt();
        }
    }

    @Override
    public void run() {
        try {
            audioPlayer.open();
            ObjectInputStream socketInputStream = new ObjectInputStream(clientSocket.getInputStream());
            while (!Thread.currentThread().isInterrupted()){
                AudioPacket receivedPacket = (AudioPacket) socketInputStream.readObject();
                audioPlayer.write(receivedPacket.audioData);
            }
            destroy();
        } catch (Exception e) {
            BaseDialog errorDialog = new BaseDialog("Error BaseDialog");
            errorDialog.add("An error occurred in pack listener").row();
            errorDialog.add(e.getMessage());
            errorDialog.show();
            Log.err(e);
            destroy();
        }
    }
}
