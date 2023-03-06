package proxMine.VoiceClient;

import org.lwjgl.openal.ALC11;
import org.lwjgl.openal.EnumerateAllExt;

class Scratch {
    public static void main(String[] args) {
        System.out.println("Default playback device: " + ALC11.alcGetString(0, EnumerateAllExt.ALC_DEFAULT_ALL_DEVICES_SPECIFIER));
    }
}