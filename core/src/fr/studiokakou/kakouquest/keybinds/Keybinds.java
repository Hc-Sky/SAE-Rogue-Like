package fr.studiokakou.kakouquest.keybinds;

import fr.studiokakou.kakouquest.GetProperties;

/**
 * Classe pour gérer les touches attribuées.
 * Cette classe permet de récupérer les touches attribuées à différentes actions dans le jeu.
 */
public class Keybinds {
    /** Touche pour se déplacer vers le haut (Z). */
    public static int UP_KEY;
    /** Touche pour se déplacer vers le bas ('S'). */
    public static int DOWN_KEY;
    /** Touche pour se déplacer vers la gauche ('Q'). */
    public static int LEFT_KEY;
    /** Touche pour se déplacer vers la droite ('D'). */
    public static int RIGHT_KEY;
    /** Touche pour effectuer un dash ('Espace'). */
    public static int DASH_KEY;

    /**
     * Met à jour les touches attribuées.
     * Cette méthode met à jour les touches en fonction du fichier de configuration.
     */
    public static void updateKeys(){
        Keybinds.UP_KEY = GetProperties.getIntProperty("KEY_UP");
        Keybinds.DOWN_KEY = GetProperties.getIntProperty("KEY_DOWN");
        Keybinds.LEFT_KEY = GetProperties.getIntProperty("KEY_LEFT");
        Keybinds.RIGHT_KEY = GetProperties.getIntProperty("KEY_RIGHT");
        Keybinds.DASH_KEY = GetProperties.getIntProperty("KEY_DASH");
    }
}
