package fr.studiokakou.kakouquest.keybinds;

import fr.studiokakou.kakouquest.GetProperties;

/**
 * Manages the keybindings for various actions.
 */
public class Keybinds {
    /** The key for moving up (Z). */
    public static int UP_KEY;
    /** The key for moving down (S). */
    public static int DOWN_KEY;
    /** The key for moving left (Q). */
    public static int LEFT_KEY;
    /** The key for moving right (D). */
    public static int RIGHT_KEY;
    /** The key for dashing (Space). */
    public static int DASH_KEY;

    /**
     * Updates the keybindings.
     * This method updates the keybindings based on the configuration file.
     */
    public static void updateKeys(){
        Keybinds.UP_KEY = GetProperties.getIntProperty("KEY_UP");
        Keybinds.DOWN_KEY = GetProperties.getIntProperty("KEY_DOWN");
        Keybinds.LEFT_KEY = GetProperties.getIntProperty("KEY_LEFT");
        Keybinds.RIGHT_KEY = GetProperties.getIntProperty("KEY_RIGHT");
        Keybinds.DASH_KEY = GetProperties.getIntProperty("KEY_DASH");
    }
}
