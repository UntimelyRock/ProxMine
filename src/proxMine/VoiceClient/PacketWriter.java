package proxMine.VoiceClient;

import arc.util.Log;
import mindustry.ui.dialogs.BaseDialog;
import proxMine.common.AudioPacket;


import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Arrays;

public class PacketWriter extends Thread {
    AudioRecorder audioRecorder;
    Socket clientSocket;
    AudioClient parentClient;

    public PacketWriter(AudioClient parentClient, Socket clientSocket) throws Exception {
        this.parentClient = parentClient;
        this.clientSocket = clientSocket;
        audioRecorder = new AudioRecorder();
    }

    public void destroy(){
        audioRecorder.destroy();
        if(!Thread.currentThread().isInterrupted()){
            Thread.currentThread().interrupt();
        }
    }

    @Override
    public void run() {
        try {
            ObjectOutputStream socketOutputStream = new ObjectOutputStream(clientSocket.getOutputStream());
            while (!Thread.currentThread().isInterrupted()){
                byte[] audioData = audioRecorder.read();
                AudioPacket packet = new AudioPacket(0, Arrays.copyOf(audioData, audioData.length));
                socketOutputStream.writeObject(packet);
            }
            destroy();
        } catch (IOException e) {
            BaseDialog errorDialog = new BaseDialog("Error BaseDialog");
            errorDialog.add("An error occurred in pack listener").row();
            errorDialog.add(e.getMessage());
            errorDialog.show();
            Log.err(e);
            destroy();
        }
    }
}
