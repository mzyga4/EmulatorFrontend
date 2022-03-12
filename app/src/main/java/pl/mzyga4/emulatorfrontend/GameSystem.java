package pl.mzyga4.emulatorfrontend;

import androidx.annotation.NonNull;

public class GameSystem {
    public String id;
    public String title;
    public String content;
    public String imgSystemLogo;
    public String imgSystemLogoBackground;
    public String imgSystemHardware;
    public String imgSystemSoftware;
    public String imgSystemManufacturer;
    public String emulatorPackage;

    @NonNull
    @Override
    public String toString() {
        return title;
    }
}