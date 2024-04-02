package fr.studiokakou.kakouquest;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Objects;
import java.util.Properties;

public class GetProperties {
    public static boolean getBoolProperty(String key) {
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

        if (Objects.equals(info, "true") || Objects.equals(info, "false")){
            return info.equals("true");
        }
        System.out.println("property is not boolean");
        return false;
    }

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
