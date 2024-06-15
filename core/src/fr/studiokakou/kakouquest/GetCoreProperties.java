package fr.studiokakou.kakouquest;

import java.io.*;
import java.util.Objects;
import java.util.Properties;

/**
 * La classe GetProperties contient des méthodes pour récupérer les propriétés à partir d'un fichier properties.
 */
public class GetCoreProperties {

    private static Properties properties = new Properties();
    private static final String PROPERTIES_FILE = "settings.properties";

    static {
        try (FileInputStream in = new FileInputStream(PROPERTIES_FILE)) {
            properties.load(in);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

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
        properties.setProperty(key, Integer.toString(value));
        try (FileOutputStream out = new FileOutputStream(PROPERTIES_FILE)) {
            properties.store(out, null);
        } catch (IOException e) {
            e.printStackTrace();
        }
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

    /**
     * Définit une propriété de chaîne de caractères dans le fichier settings.properties.
     *
     * @param key   La clé de la propriété
     * @param value La nouvelle valeur de la propriété
     */
    public static void setStringProperty(String key, String value) {
        properties.setProperty(key, value);
        try (FileOutputStream out = new FileOutputStream(PROPERTIES_FILE)) {
            properties.store(out, null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
