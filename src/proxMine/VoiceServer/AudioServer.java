package proxMine.VoiceServer;

import proxMine.common.AudioPacket;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AudioServer {
    List<VoiceUser> connectedUsers = Collections.synchronizedList(new ArrayList<>());
    MemberListener memberListener;

    public AudioServer(int port) throws IOException {
        memberListener = new MemberListener(this, port);
    }


    public void addUser(VoiceUser newUser){
        Collections.synchronizedList(connectedUsers).add(newUser);
    }

    public void broadcast(AudioPacket audioPacket){
        Thread broadcastThread = new Thread(new Broadcaster(audioPacket, Collections.synchronizedList(connectedUsers)));
        broadcastThread.start();
    }



}
