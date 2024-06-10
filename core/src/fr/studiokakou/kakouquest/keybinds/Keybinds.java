package fr.studiokakou.kakouquest.keybinds;

import fr.studiokakou.kakouquest.GetCoreProperties;

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
    /** Touche pour interagir ('E'). */
    public static int INTERRACT_KEY;
    /** Touche pour ouvrir l'inventaire ('Tab'). */
    public static int INVENTORY_KEY;


    /**
     * Met à jour les touches attribuées.
     * Cette méthode met à jour les touches en fonction du fichier de configuration.
     */
    public static void updateKeys(){
        Keybinds.UP_KEY = GetCoreProperties.getIntProperty("KEY_UP");
        Keybinds.DOWN_KEY = GetCoreProperties.getIntProperty("KEY_DOWN");
        Keybinds.LEFT_KEY = GetCoreProperties.getIntProperty("KEY_LEFT");
        Keybinds.RIGHT_KEY = GetCoreProperties.getIntProperty("KEY_RIGHT");
        Keybinds.DASH_KEY = GetCoreProperties.getIntProperty("KEY_DASH");
        Keybinds.INTERRACT_KEY = GetCoreProperties.getIntProperty(("KEY_INTERRACT"));
    }
}
