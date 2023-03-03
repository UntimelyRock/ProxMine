package proxMine;

import arc.ApplicationListener;
import arc.Core;
import arc.Events;
import arc.input.KeyCode;
import arc.util.Log;
import arc.util.Time;
import mindustry.game.EventType;
import mindustry.mod.Mod;
import mindustry.ui.dialogs.BaseDialog;
import proxMine.VoiceClient.AudioClient;
import proxMine.VoiceServer.AudioServer;

import javax.sound.sampled.LineUnavailableException;

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
            //show dialog upon startup
            Time.runTask(10f, () -> {
                BaseDialog dialog = new BaseDialog("ProxMine is active");
                dialog.cont.add("""
                        *****WARNING.*****
                        ProxMine is a network based mod, the creator is not a network engineer or security specialist
                        I cannot ensure this mod is secure. Only use this with people you trust.
                        and am not responsible for any damage caused. You have been warned
                        This will also eat up a TON of bandwidth.
                        Please for the love of the scientific method do NOT use this on a metered connection.
                                        """).row();
                dialog.cont.button("I understand", dialog::hide).size(300f, 50f);
                dialog.show();

                try {
                    audioClient = new AudioClient();
                    audioServer = new AudioServer();
                } catch (Exception ex) {
                    BaseDialog errorDialog = new BaseDialog("error");
                    errorDialog.cont.add("There was an error starting the AudioClient").row();
                    errorDialog.cont.add(ex.getMessage()).row();
                }
            });


        });

        Events.on(EventType.ClientPreConnectEvent.class, clientPreConnectEvent -> {
            BaseDialog dialog = new BaseDialog("ProxMine is active");
            dialog.cont.add("""
                        (clientPreConnectEvent)
                        Ready to connect to server, connect?
                        """).row();
            dialog.cont.button("Connect", new VoiceClientConnector(this, clientPreConnectEvent.host.address, clientPreConnectEvent.host.port, dialog)).size(300f, 50f);
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
            dialog.cont.button("Start Voice Server", new VoiceServerStarter(this, dialog)).size(300f, 50f);
            dialog.cont.button("!!Close without starting!!", dialog::hide).size(300f, 50f);

            dialog.show();
        });
        Core.app.post(new SettingMenu(this));
    }

    @Override
    public void loadContent(){
        Log.info("Loading some example content.");
    }

    public static class VoiceServerStarter implements Runnable{
        ProxMine proxMine;
        BaseDialog dialog;

        public VoiceServerStarter(ProxMine proxMine, BaseDialog dialog) {
            this.proxMine = proxMine;
            this.dialog = dialog;
        }

        @Override
        public void run() {

            dialog.hide();
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

            dialog.hide();
        }
    }



}

