package proxMine;

import arc.Core;
import arc.input.KeyCode;
import mindustry.ui.dialogs.BaseDialog;

public class SettingMenu implements Runnable{
    ProxMine proxMineMod;
    public SettingMenu(ProxMine proxMineMod){
        this.proxMineMod = proxMineMod;
    }

    @Override
    public void run() {
        if(Core.input.keyDown(KeyCode.f12)){
            BaseDialog dialog = new BaseDialog("proxMine-settings");
            dialog.cont.add("ProxMine Settings").row();
            dialog.cont.button("Save and close", dialog::hide).size(300f, 50f);
            dialog.show();
        }
    }
}
