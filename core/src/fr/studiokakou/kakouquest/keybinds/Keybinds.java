package fr.studiokakou.kakouquest.keybinds;

import fr.studiokakou.kakouquest.GetProperties;

public class Keybinds {
    public static int UP_KEY;
    public static int DOWN_KEY;
    public static int LEFT_KEY;
    public static int RIGHT_KEY;
    public static int DASH_KEY;

    public static void updateKeys(){
        Keybinds.UP_KEY = GetProperties.getIntProperty("KEY_UP");
        Keybinds.DOWN_KEY = GetProperties.getIntProperty("KEY_DOWN");
        Keybinds.LEFT_KEY = GetProperties.getIntProperty("KEY_LEFT");
        Keybinds.RIGHT_KEY = GetProperties.getIntProperty("KEY_RIGHT");
        Keybinds.DASH_KEY = GetProperties.getIntProperty("KEY_DASH");
    }
}
