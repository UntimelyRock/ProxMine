package proxMine.VoiceServer;

import arc.util.Log;

import java.io.IOException;
import java.net.ServerSocket;

public class MemberListener extends Thread{
    AudioServer parentServer;
    ServerSocket serverSocket;

    MemberListener(AudioServer parentServer, int port) throws IOException {
        this.parentServer = parentServer;
        this.serverSocket = new ServerSocket(port);
    }

    @Override
    public void run() {
        super.run();
        try {
            while (!Thread.currentThread().isInterrupted()){
                parentServer.addUser(new VoiceUser(serverSocket.accept(), parentServer));
            }
            serverSocket.close();
        } catch (IOException e) {
            Log.err(e);
            throw new RuntimeException(e);
        }

    }
}
