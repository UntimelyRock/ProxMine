package proxMine;

import arc.ApplicationListener;
import arc.Core;
import arc.Events;
import arc.audio.AudioSource;
import arc.audio.Soloud;
import arc.func.Cons;
import arc.func.Floatc;
import arc.input.KeyCode;
import arc.net.Client;
import arc.util.CommandHandler;
import arc.util.Log;
import arc.util.Time;
import mindustry.Vars;
import mindustry.core.UI;
import mindustry.game.EventType;
import mindustry.gen.Groups;
import mindustry.gen.Player;
import mindustry.mod.Mod;
import mindustry.ui.dialogs.BaseDialog;
//import org.lwjgl.openal.AL;
import mindustry.ui.dialogs.SettingsMenuDialog;
import org.lwjgl.openal.AL;
import org.lwjgl.openal.ALCCapabilities;
import proxMine.VoiceClient.AudioClient;
import proxMine.VoiceServer.AudioServer;
import proxMine.ui.SettingLabel;

import java.io.IOException;

import static arc.Core.bundle;
import static arc.Core.settings;

public class ProxMine extends Mod {
    protected AudioClient audioClient;
    protected AudioServer audioServer;
    protected ProxMineSettings proxMineSettings = null;


    public ProxMineSettings getProxMineSettings() {
        return proxMineSettings;
    }

    public ProxMine() {
        Log.info("Loading ProxMine");

        //listen for game load event
        Events.on(EventType.ClientLoadEvent.class, e -> {
            Log.info(ProxMineInfo.buildFullInfoString());

            //show dialog upon startup
            Time.runTask(10f, () -> {


                Vars.ui.settings.addCategory(bundle.get("setting.pmine-title"), s ->{
                    s.pref(new SettingLabel("pmine-audio-settings", 3));
                    s.sliderPref("master-output-volume", 50, 0, 100, 1, str -> str+"%");
                    s.pref(new SettingLabel("pmine-audio-settings", 3));



                });

                BaseDialog dialog = new BaseDialog("ProxMine is active");
                dialog.cont.add(bundle.get("startup.pmine-start-warning")).row();
                dialog.cont.button("I understand", dialog::hide).size(300f, 50f).row();
                dialog.cont.button("view Open AL info and credits", new CreditsAndInfoMenu()).size(400,50);
                dialog.show();
            });


        });

        Events.on(EventType.ClientPreConnectEvent.class, clientPreConnectEvent -> {
            BaseDialog dialog = new BaseDialog("ProxMine is active");
            dialog.cont.add("""
                        (clientPreConnectEvent)
                        Ready to connect to server, connect?
                        """).row();
            dialog.cont.button("Connect", new VoiceClientConnector(this, clientPreConnectEvent.host.address, clientPreConnectEvent.host.port + 1, dialog)).size(300f, 50f);
            dialog.cont.button("Close without connecting", dialog::hide).size(300f, 50f);
            dialog.show();
        });

        Events.on(EventType.HostEvent.class, hostEvent -> {
            BaseDialog dialog = new BaseDialog("ProxMine is active");
            dialog.cont.add("""
                        ProxMine Audio server is now launching a ProxMine server. 
                        Anyone who joins should be able to hear you
                        !!Warning!!, closing without starting the server will cause errors for anyone who joins
                        and tries to connect to it.
                        """).row();
            dialog.cont.button("Start Voice Server", new VoiceServerStarter(this, dialog, Vars.port + 1)).size(300f, 50f);
            dialog.cont.button("!!Close without starting!!", dialog::hide).size(300f, 50f);

            dialog.show();
        });
        Core.app.post(new SettingMenu(this));
    }

    @Override
    public void registerClientCommands(CommandHandler handler) {
        super.registerClientCommands(handler);
        handler.register("voip", "", "Connect to proximity chat of current server", arg -> {

        });
    }

    @Override
    public void registerServerCommands(CommandHandler handler) {
        super.registerServerCommands(handler);
    }

    @Override
    public void loadContent(){
        Log.info("Loading some example content.");
    }

    public static class VoiceServerStarter implements Runnable{
        ProxMine proxMine;
        BaseDialog dialog;

        int port;

        public VoiceServerStarter(ProxMine proxMine, BaseDialog dialog, int port) {
            this.proxMine = proxMine;
            this.dialog = dialog;
            this.port = port;
        }

        @Override
        public void run() {
            try {
                proxMine.audioServer = new AudioServer(port);

                if(!Vars.headless){
                    proxMine.audioClient = new AudioClient();
                    proxMine.audioClient.connect("localhost", port);
                }



            } catch (Exception e) {
                Log.err(e);
                BaseDialog errorDialog = new BaseDialog("error");
                errorDialog.cont.add("There was an error starting the AudioClient").row();
                errorDialog.cont.add(e.getMessage()).row();
                errorDialog.cont.add(e.getCause().getMessage()).row();
                errorDialog.cont.add(e.getStackTrace().toString()).row();
            }
            dialog.hide();
        }
    }

    public static class CreditsAndInfoMenu implements Runnable{

        @Override
        public void run() {
            BaseDialog creditsMenu = new BaseDialog("creditsMenu");
            creditsMenu.cont.button("Credits", new Runnable() {
                @Override
                public void run() {
                    //TODO: Make a credits screen
                }
            }).size(300f, 50);

            creditsMenu.cont.button("Info", new Runnable() {
                @Override
                public void run() {
                    BaseDialog infoDialog = new BaseDialog("infoDialog");
                    infoDialog.cont.add(ProxMineInfo.buildFullInfoString()).row();
                    infoDialog.cont.button("close", infoDialog::hide).size(100,50);
                    infoDialog.show();
                }
            }).size(400f, 75);

            creditsMenu.cont.button("Acknowledgements", new Runnable() {
                @Override
                public void run() {
                    //TODO: https://www.lwjgl.org/license
                }
            }).size(300f, 50);

            creditsMenu.cont.row();
            creditsMenu.cont.button("", creditsMenu::hide).size(0.01f,0.01f);
            creditsMenu.cont.button("Close", creditsMenu::hide).size(100f, 50f).center();

            creditsMenu.show();

        }
    }

    public static class VoiceClientConnector implements Runnable{
        ProxMine proxMine;
        String address;
        int port;
        BaseDialog dialog;

        public VoiceClientConnector(ProxMine proxMine, String address, int port, BaseDialog dialog) {
            this.proxMine = proxMine;
            this.address = address;
            this.port = port;
            this.dialog = dialog;
        }

        @Override
        public void run() {
            proxMine.audioClient = new AudioClient();
            try {
                proxMine.audioClient.connect(address, port);
            } catch (Exception e) {
                Log.err(e);
                throw new RuntimeException(e);

            }

            dialog.hide();
        }
    }



}

