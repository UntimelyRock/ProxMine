package proxMine.ui;

import mindustry.ui.dialogs.BaseDialog;
import proxMine.common.ProxMineInfo;

public class CreditsAndInfoMenu implements Runnable{

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
