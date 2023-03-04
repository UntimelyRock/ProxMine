package proxMine.VoiceServer;

import arc.util.Log;
import proxMine.common.AudioPacket;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class VoiceUser extends Thread{
    AudioServer parentServer;
    Socket toClientSocket;
    ObjectOutputStream objectOutputStream;
    ObjectInputStream objectInputStream;


    VoiceUser(Socket toClientSocket, AudioServer parentServer) throws IOException {
        this.toClientSocket = toClientSocket;
        this.parentServer = parentServer;
        objectOutputStream = new ObjectOutputStream(toClientSocket.getOutputStream());
        objectInputStream = new ObjectInputStream(toClientSocket.getInputStream());
    }

    public void send(AudioPacket audioPacket) throws IOException {
        objectOutputStream.writeObject(audioPacket);
    }

    @Override
    public void run() {
        super.run();
        while (!Thread.currentThread().isInterrupted()){
            try {
                parentServer.broadcast((AudioPacket) objectInputStream.readObject());
            } catch (IOException | ClassNotFoundException e) {
                Log.err(e);
                throw new RuntimeException(e);
            }
        }
        try {
            toClientSocket.close();
            objectInputStream.close();
            objectOutputStream.close();
        } catch (IOException e) {
            Log.err(e);
            throw new RuntimeException(e);
        }
    }
}
