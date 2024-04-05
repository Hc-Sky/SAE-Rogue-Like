package fr.studiokakou.kakouquest;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Objects;
import java.util.Properties;

/**
 * La classe GetProperties contient des méthodes pour récupérer les propriétés à partir d'un fichier properties.
 */
public class GetProperties {

    /**
     * Récupère une propriété booléenne à partir du fichier settings.properties.
     *
     * @param key La clé de la propriété
     * @return La valeur booléenne correspondant à la propriété
     */
    public static boolean getBoolProperty(String key) {
        Properties prop = loadPropertiesFile();
        String info = prop.getProperty(key);
        return Objects.equals(info, "true");
    }

    /**
     * Récupère une propriété entière à partir du fichier settings.properties.
     *
     * @param key La clé de la propriété
     * @return La valeur entière correspondant à la propriété
     */
    public static int getIntProperty(String key) {
        Properties prop = loadPropertiesFile();
        String info = prop.getProperty(key);
        return Integer.parseInt(info);
    }

    /**
     * Récupère une propriété de chaîne de caractères à partir du fichier settings.properties.
     *
     * @param key La clé de la propriété
     * @return La valeur de chaîne de caractères correspondant à la propriété
     */
    public static String getStringProperty(String key) {
        Properties prop = loadPropertiesFile();
        return prop.getProperty(key);
    }

    /**
     * Charge le fichier properties.
     *
     * @return Les propriétés chargées depuis le fichier settings.properties
     */
    private static Properties loadPropertiesFile() {
        Properties prop = new Properties();
        try (FileReader reader = new FileReader("settings.properties")) {
            prop.load(reader);
        } catch (FileNotFoundException e) {
            throw new RuntimeException("Le fichier settings.properties n'a pas été trouvé", e);
        } catch (IOException e) {
            throw new RuntimeException("Erreur lors de la lecture du fichier settings.properties", e);
        }
        return prop;
    }
}