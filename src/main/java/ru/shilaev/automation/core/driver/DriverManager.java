package ru.shilaev.automation.core.driver;

import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.shilaev.automation.config.CapabilitiesReader;
import ru.shilaev.automation.config.EnvironmentConfig;
import ru.shilaev.automation.core.exceptions.DriverException;

import java.net.MalformedURLException;
import java.net.URL;

public class DriverManager {
    private final static Logger log = LoggerFactory.getLogger(DriverManager.class);
    private static AndroidDriver driver;

    /**
     * Создает и возвращает драйвер Appium
     */
    public static AndroidDriver getDriver() {
        if (driver == null) {
            createDriver();
        }
        return driver;
    }

    /**
     * Создает драйвер
     */
    private static void createDriver() {
        try {
            String deviceName = EnvironmentConfig.getDeviceName();
            if (deviceName == null || deviceName.isEmpty()) {
                log.error("Device name not specified. Use -Ddevice.name=.... or add device.name=.... to .property file");
                throw new DriverException("Device name not specified. Use -Ddevice.name=.... or add device.name=.... to .property file");
            }

            DesiredCapabilities capabilities = CapabilitiesReader.load(deviceName);

            String appiumUrl = EnvironmentConfig.get("appium.url");
            log.info("Using specified appium URL: {}", appiumUrl);
            if (appiumUrl == null || appiumUrl.isEmpty()) {
                appiumUrl = "http://127.0.0.1:4723";
                log.info("Using default appium URL: {}", appiumUrl);
            }

            driver = new AndroidDriver(new URL(appiumUrl), capabilities);
            log.info("Driver created: {}", driver);
        } catch (MalformedURLException e) {
            log.error("Failed to create driver: " + e);
            throw new DriverException("Failed to create driver: ", e);
        }
    }

    /**
     * Закрывает драйвер
     */
    public static void quitDriver() {
        if (driver != null) {
            driver.quit();
            driver = null;
            log.info("Driver quit");
        }
    }
}
