package fr.studiokakou.kakouquest.keybinds;

import fr.studiokakou.kakouquest.GetProperties;

/**
 * L'attribution des taches Keybinds.
 */
public class Keybinds {
    /**
     * La touche du Haut (Z).
     */
    public static int UP_KEY;
    /**
     * La touche du bas 'S'.
     */
    public static int DOWN_KEY;
    /**
     * La touche de gauche 'Q'.
     */
    public static int LEFT_KEY;
    /**
     * La touche de droite 'D'.
     */
    public static int RIGHT_KEY;
    /**
     * La touche de dash 'Espace'.
     */
    public static int DASH_KEY;

    /**
     * Met à jour les touches.
     * Permet de mettre à jour les touches en fonction du fichier de configuration.
     */
    public static void updateKeys(){
        Keybinds.UP_KEY = GetProperties.getIntProperty("KEY_UP");
        Keybinds.DOWN_KEY = GetProperties.getIntProperty("KEY_DOWN");
        Keybinds.LEFT_KEY = GetProperties.getIntProperty("KEY_LEFT");
        Keybinds.RIGHT_KEY = GetProperties.getIntProperty("KEY_RIGHT");
        Keybinds.DASH_KEY = GetProperties.getIntProperty("KEY_DASH");
    }
}
