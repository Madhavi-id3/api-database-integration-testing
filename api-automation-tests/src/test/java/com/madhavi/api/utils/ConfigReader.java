
package com.madhavi.api.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigReader {

    private static final Properties properties = new Properties();

    static {

        try (InputStream input = ConfigReader.class
                .getClassLoader()
                .getResourceAsStream("config.properties")) {

            if (input == null) {
                throw new RuntimeException(
                        "config.properties file not found");
            }

            properties.load(input);

        } catch (IOException e) {
            throw new RuntimeException(
                    "Unable to load config.properties", e);
        }
    }

    public static String getProperty(String key) {

        String value = properties.getProperty(key);

        if (value == null || value.isBlank()) {
            throw new RuntimeException(
                    "Missing configuration property: " + key);
        }

        return value.trim();
    }
}
