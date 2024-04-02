package fr.studiokakou.kakouquest;

import java.io.FileReader;
import java.io.IOException;
import java.util.Objects;
import java.util.Properties;

public class GetProperties {
    public static boolean getBoolProperty(String key) throws IOException {
        FileReader reader = new FileReader("settings.properties");

        Properties prop = new Properties();
        prop.load(reader);

        String info = prop.getProperty(key);

        if (Objects.equals(info, "true") || Objects.equals(info, "false")){
            return info.equals("true");
        }
        System.out.println("property is not boolean");
        return false;
    }

    public static int getIntProperty(String key) throws IOException {
        FileReader reader = new FileReader("settings.properties");

        Properties prop = new Properties();
        prop.load(reader);

        String info = prop.getProperty(key);

        return Integer.parseInt(info);
    }

    public static String getStringProperty(String key) throws IOException {
        FileReader reader = new FileReader("settings.properties");

        Properties prop = new Properties();
        prop.load(reader);

        return prop.getProperty(key);
    }
}
