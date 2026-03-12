package com.insider.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigReader {

    private ConfigReader() {}

    private static final Logger log = LoggerFactory.getLogger(ConfigReader.class);
    private static final Properties properties = new Properties();

    static {
        try (FileInputStream input = new FileInputStream("src/test/resources/config.properties")) {
            properties.load(input);
            log.debug("config.properties loaded");
        } catch (IOException e) {
            throw new RuntimeException("Failed to load config.properties", e);
        }
    }


    public static String get(String key) {
        return System.getProperty(key, properties.getProperty(key));
    }
}
