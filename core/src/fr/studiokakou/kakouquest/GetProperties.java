package fr.studiokakou.kakouquest;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Objects;
import java.util.Properties;

/**
 * The GetProperties class provides methods to read properties from a properties file.
 */
public class GetProperties {

    /**
     * Reads a boolean property from the properties file.
     *
     * @param key The key of the property.
     * @return The boolean value of the property.
     * @throws RuntimeException if an error occurs while reading the properties file.
     */
    public static boolean getBoolProperty(String key) {
        FileReader reader;
        try {
            reader = new FileReader("settings.properties");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        Properties prop = new Properties();
        try {
            prop.load(reader);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        String info = prop.getProperty(key);

        try {
            reader.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        if (Objects.equals(info, "true") || Objects.equals(info, "false")){
            return info.equals("true");
        }
        System.out.println("property is not boolean");
        return false;
    }

    /**
     * Reads an integer property from the properties file.
     *
     * @param key The key of the property.
     * @return The integer value of the property.
     * @throws RuntimeException if an error occurs while reading the properties file.
     */
    public static int getIntProperty(String key)  {
        FileReader reader = null;
        try {
            reader = new FileReader("settings.properties");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        Properties prop = new Properties();
        try {
            prop.load(reader);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        String info = prop.getProperty(key);

        try {
            reader.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return Integer.parseInt(info);
    }

    /**
     * Reads a string property from the properties file.
     *
     * @param key The key of the property.
     * @return The string value of the property.
     * @throws RuntimeException if an error occurs while reading the properties file.
     */
    public static String getStringProperty(String key)  {
        FileReader reader = null;
        try {
            reader = new FileReader("settings.properties");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        Properties prop = new Properties();
        try {
            prop.load(reader);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try {
            reader.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return prop.getProperty(key);
    }
}