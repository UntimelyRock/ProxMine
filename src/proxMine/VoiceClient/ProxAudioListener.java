package proxMine.VoiceClient;

import org.lwjgl.openal.AL10;
import org.lwjgl.openal.AL11;

import java.util.Vector;

public class ProxAudioListener {
    public ProxAudioListener(float x, float y){
        AL11.alListener3f(AL11.AL_POSITION,x ,y ,0);
        AL11.alListener3f(AL11.AL_VELOCITY, 0,0,0);
        AL11.alListener3f(AL11.AL_ORIENTATION, 0,0,0);
    }

    public void setVelocity(int x, int y){
        AL11.alListener3f(AL11.AL_VELOCITY, x, y, 0);
    }

    public void setPosition(int x, int y){
        AL11.alListener3f(AL11.AL_POSITION, x, y, 0);
    }

}
