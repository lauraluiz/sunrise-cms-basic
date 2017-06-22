package com.commercetools.sunrise;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

final class PropertiesUtils {

    private PropertiesUtils() {
    }

    static Properties loadProperties() {
        Properties properties = new Properties();
        FileInputStream inputStream = null;
        try {
            inputStream = new FileInputStream("config.properties");
            properties.load(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return properties;
    }
}
