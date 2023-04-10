package proxMine.ui;

import mindustry.gen.Tex;
import mindustry.ui.dialogs.SettingsMenuDialog;

public class SettingLabel extends SettingsMenuDialog.SettingsTable.Setting {
    float buffer = 3f;
    public SettingLabel(String name) {
        super(name);
    }

    public SettingLabel(String name, float buffer){
        super(name);
        this.buffer = buffer;
    }

    @Override
    public void add(SettingsMenuDialog.SettingsTable settingsTable) {
        settingsTable.table(t -> {
            t.image(Tex.clear).height(buffer);
            t.add(this.title);
        }).get().background(Tex.underline);
        settingsTable.row();
    }
}
