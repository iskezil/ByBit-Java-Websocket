package com.justice.services;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class GetProperties {

    public String getProperty(String key) throws IOException {
        Properties properties = ReadPropertyFile();
        return properties.getProperty(key);
    }
    private Properties ReadPropertyFile() throws IOException {
        String configFilePath = "src/config.properties";
        FileInputStream propsInput = new FileInputStream(configFilePath);
        Properties prop = new Properties();
        prop.load(propsInput);
        return prop;
    }
}
