package org.example;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Properties;

public class Config {

    public static HashMap<String, String> loadDBCredentials() {

        HashMap<String, String> configs = new HashMap<>();

        String configFilePath = "src/main/resources/database.properties";

        try {
            FileInputStream propsInput = new FileInputStream(configFilePath);
            Properties prop = new Properties();
            prop.load(propsInput);

            configs.put("username", prop.getProperty("username"));
            configs.put("password", prop.getProperty("password"));
            configs.put("database_url", prop.getProperty("database_url"));
            configs.put("database_driver", prop.getProperty("database_driver"));

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return configs;
    }
}
