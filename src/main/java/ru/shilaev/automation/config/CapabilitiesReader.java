package ru.shilaev.automation.config;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.shilaev.automation.core.exceptions.ConfigException;

import java.io.File;
import java.io.IOException;
import java.util.Map;

public class CapabilitiesReader {
    private static final Logger log = LoggerFactory.getLogger(CapabilitiesReader.class);
    private static final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * Загружает capabilities для устройств
     *
     * @param deviceName имя устройства (например, Emulator_Medium_Phone_API_36)
     * @return DesiredCapabilities для Appium
     */
    public static DesiredCapabilities load(String deviceName) {
        String path = "config/capabilities/android/" + deviceName + ".json";

        File file = new File(path);
        if (!file.exists()) {
            log.error("Capabilities file not found: {}", path);
            throw new ConfigException("Capabilities file not found: " + path);
        }

        try {
            Map<String, Object> capsMap = objectMapper.readValue(file, new TypeReference<>() {});

            DesiredCapabilities capabilities = new DesiredCapabilities();
            capsMap.forEach(capabilities::setCapability);

            log.info("Capabilities loaded: {}", capabilities);
            return capabilities;
        } catch (IOException e) {
            log.error("Can`t load capabilities: {}", path, e);
            throw new ConfigException("Can`t load capabilities: " + path, e);
        }
    }

    /**
     * Загружает capabilities и добавляет настройки из окружения
     *
     * @param deviceName имя устройства
     * @return объединенные DesiredCapabilities
     */
    public static DesiredCapabilities loadWithEnvironment(String deviceName) {
        DesiredCapabilities caps = load(deviceName);

        String appPackage = EnvironmentConfig.get("app.package");
        if (appPackage != null) {
            caps.setCapability("appPackage", appPackage);
            log.info("appPackage loaded: {}", appPackage);
        }

        String appActivity = EnvironmentConfig.get("app.activity");
        if (appActivity != null) {
            caps.setCapability("appActivity", appActivity);
            log.info("appActivity loaded: {}", appActivity);
        }

        return caps;
    }

}
