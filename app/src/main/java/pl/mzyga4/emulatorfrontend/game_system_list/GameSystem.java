package pl.mzyga4.emulatorfrontend.game_system_list;

import androidx.annotation.NonNull;

import java.io.Serializable;

public class GameSystem implements Serializable {
    public String id;
    public String title;
    public String content;
    public String imgSystemLogo;
    public String imgSystemLogoBackground;
    public String imgSystemHardware;
    public String imgSystemSoftware;
    public String imgSystemManufacturer;
    public String emulatorPackage;
    public String gamesListSource;

    @NonNull
    @Override
    public String toString() {
        return title;
    }
}