package proxMine.VoiceServer;

import arc.util.Log;
import proxMine.common.AudioPacket;

import java.io.IOException;
import java.util.List;

public class Broadcaster implements Runnable {
    AudioPacket audioPacket;
    List<VoiceUser> users;

    Broadcaster(AudioPacket audioPacket, List<VoiceUser> users){
        this.audioPacket = audioPacket;
        this.users = users;
    }

    @Override
    public void run() {
        for (VoiceUser user: users) {
            try {
                user.send(audioPacket);
            } catch (IOException e) {
                Log.err(e);
                throw new RuntimeException(e);
            }
        }

    }
}
