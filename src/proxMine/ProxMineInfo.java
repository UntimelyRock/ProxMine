package proxMine;

import org.lwjgl.openal.*;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import static org.lwjgl.openal.ALC11.alcCloseDevice;
import static org.lwjgl.openal.ALC11.alcDestroyContext;
import static org.lwjgl.openal.ALC11.alcMakeContextCurrent;
import static org.lwjgl.openal.ALC11.alcCreateContext;
import static org.lwjgl.openal.ALC11.ALC_DEFAULT_DEVICE_SPECIFIER;
import static org.lwjgl.openal.ALC11.alcOpenDevice;
import static org.lwjgl.openal.EXTThreadLocalContext.alcSetThreadContext;
import static org.lwjgl.system.MemoryUtil.NULL;

public class ProxMineInfo {

    public static void main(String[] args) {
        System.out.println(buildFullInfoString());
    }

    public static String buildFullInfoString(){
        String[] args = new String[0];
        long device = alcOpenDevice(args.length == 0 ? null : args[0]);
        if (device == NULL) {
            throw new IllegalStateException("Failed to open an OpenAL device.");
        }

        ALCCapabilities deviceCaps = ALC.createCapabilities(device);

        long context = alcCreateContext(device, (IntBuffer)null);

        boolean useTLC = deviceCaps.ALC_EXT_thread_local_context && alcSetThreadContext(context);
        if (!useTLC) {
            if (!alcMakeContextCurrent(context)) {
                throw new IllegalStateException();
            }
        }

        AL.createCapabilities(deviceCaps);
        String stringBuilder = getALVersionInfo() + "\n\n" +
                getAudioDevices(device, deviceCaps);


        alcMakeContextCurrent(NULL);
        if (useTLC) {
            AL.setCurrentThread(null);
        } else {
            AL.setCurrentProcess(null);
        }

        alcDestroyContext(context);
        alcCloseDevice(device);

        return stringBuilder
                .replace(",", "\n")
                .replace("}", "")
                .replace("{", "")
                .replace("capture=[", "Capture devices\n ")
                .replace("playback=[", "\nPlayback devices\n ")
                .replace("]", "")
                .replace("OpenAL Soft on", "")
                ;
    }

    public static String getALVersionInfo(){
        StringBuilder alInfo = new StringBuilder("OpenALInfo:");
        alInfo.append("\n   Version:").append(AL11.alGetString(AL11.AL_VERSION));
        alInfo.append("\n   Renderer:").append(AL11.alGetString(AL11.AL_RENDERER));
        alInfo.append("\n   Vendor:").append(AL11.alGetString(AL11.AL_VENDOR));
        return alInfo.toString();
    }

    public static HashMap<String, List<String>> getAudioDevices(long device, ALCCapabilities capabilities){
        HashMap<String, List<String>> output = new HashMap<>();
        if(capabilities.ALC_ENUMERATION_EXT){
            if(capabilities.ALC_ENUMERATE_ALL_EXT){
                output.put("playback", Objects.requireNonNull(ALUtil.getStringList(NULL, EnumerateAllExt.ALC_ALL_DEVICES_SPECIFIER)));
            }else{
                output.put("playback", Objects.requireNonNull(ALUtil.getStringList(NULL, ALC11.ALC_DEVICE_SPECIFIER)));
            }
            output.put("capture", Objects.requireNonNull(ALUtil.getStringList(NULL, ALC11.ALC_CAPTURE_DEVICE_SPECIFIER)));
        }
        return output;
    }
}
