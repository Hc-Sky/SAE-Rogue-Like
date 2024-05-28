package fr.studiokakou.kakouquest;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Objects;
import java.util.Properties;

/**
 * La classe GetProperties contient des méthodes pour récupérer les propriétés à partir d'un fichier properties.
 */
public class GetProperties {

    private static final String PROPERTIES_FILE = "settings.properties";

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
     * Définit une propriété entière dans le fichier settings.properties.
     *
     * @param key   La clé de la propriété
     * @param value La nouvelle valeur entière de la propriété
     */
    public static void setIntProperty(String key, int value) {
        Properties prop = loadPropertiesFile();
        prop.setProperty(key, String.valueOf(value));
        savePropertiesFile(prop);
    }

    /**
     * Charge le fichier properties.
     *
     * @return Les propriétés chargées depuis le fichier settings.properties
     */
    private static Properties loadPropertiesFile() {
        Properties prop = new Properties();
        try (FileReader reader = new FileReader(PROPERTIES_FILE)) {
            prop.load(reader);
        } catch (IOException e) {
            throw new RuntimeException("Erreur lors de la lecture du fichier settings.properties", e);
        }
        return prop;
    }

    /**
     * Enregistre les propriétés dans le fichier settings.properties.
     *
     * @param prop Les propriétés à enregistrer
     */
    private static void savePropertiesFile(Properties prop) {
        try (FileWriter writer = new FileWriter(PROPERTIES_FILE)) {
            prop.store(writer, null);
        } catch (IOException e) {
            throw new RuntimeException("Erreur lors de l'écriture dans le fichier settings.properties", e);
        }
    }
}
