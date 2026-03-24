package ru.shilaev.automation.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.shilaev.automation.core.exceptions.ConfigException;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class EnvironmentConfig {
    private static final Logger log = LoggerFactory.getLogger(EnvironmentConfig.class);
    private static final Properties props = new Properties();

    static {
        String env = System.getProperty("env", "dev"); //По дефолту выбираем env=dev.properties
        String path = "config/environments/" + env + ".properties";

        try (FileInputStream fis = new FileInputStream(path)) {
            props.load(fis);
            log.info("Environment: {} config loaded by path: {}", env, path);
        } catch (IOException e) {
            log.error("Can`t load capabilities: {}", path, e);
            throw new ConfigException("Can`t load capabilities: " + path, e);
        }
    }

    /**
     * Достает значение из переменных окружения
     * @param key - имя переменной (например device.name)
     * @return String - значение переменной
     */
    public static String get(String key) {
        String value = System.getProperty(key);
        if (value != null) return value;

        value = System.getenv(key);
        if (value != null) return value;

        return props.getProperty(key);
    }

    public static String getEnvironmentName() {
        return get("environment.name");
    }

    public static String getDeviceName() {
        return get("device.name");
    }
}
