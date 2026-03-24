package ru.shilaev.automation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.shilaev.automation.config.EnvironmentConfig;

import java.io.IOException;

public class Main {
    private static final Logger log = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) throws IOException {
        String deviceName = EnvironmentConfig.getDeviceName();
        String environmentName = EnvironmentConfig.getEnvironmentName();

        System.out.println(deviceName);
        System.out.println(environmentName);
    }
}