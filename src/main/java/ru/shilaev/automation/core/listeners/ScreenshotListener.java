package ru.shilaev.automation.core.listeners;

import io.appium.java_client.android.AndroidDriver;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestExecutionExceptionHandler;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.shilaev.automation.core.driver.DriverManager;
import ru.shilaev.automation.core.exceptions.AutomationException;

import java.io.File;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Слушатель для JUnit 5, который при падении теста:
 * - Делает скриншот экрана
 * - Сохраняет UI иерархию (XML)
 * - Сохраняет стектрейс ошибки
 */
public class ScreenshotListener implements TestExecutionExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(ScreenshotListener.class);
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH-mm-ss");

    @Override
    public void handleTestExecutionException(ExtensionContext context, Throwable throwable) throws Throwable {
        saveArtifacts(context, throwable);
        throw throwable;
    }

    /**
     * Сохраняет все артефакты при падении теста
     */
    private void saveArtifacts(ExtensionContext context, Throwable throwable) {
        try {
            // Получаем информацию о тесте
            String className = context.getRequiredTestClass().getSimpleName();
            String methodName = context.getRequiredTestMethod().getName();
            String testName = className + "." + methodName;
            String tags = getTestTags(context);
            String timestamp = LocalDateTime.now().format(TIME_FORMATTER);
            String dateDir = LocalDateTime.now().format(DATE_FORMATTER);

            // Базовое имя файла
            String baseName = String.format("%s_%s_%s", timestamp, tags, testName);

            // Создаем директорию logs/screenshots/YYYY-MM-DD/
            Path dir = Paths.get("logs/screenshots/" + dateDir);
            Files.createDirectories(dir);

            // 1. Скриншот
            saveScreenshot(dir, baseName);

            // 2. UI иерархия (XML)
            saveUiHierarchy(dir, baseName);

            // 3. Стектейс ошибки
            saveErrorStackTrace(dir, baseName, throwable);

            log.info("Artifacts saved to: {}", dir);

        } catch (Exception e) {
            log.error("Failed to save artifacts", e);
            throw new AutomationException("Failed to save artifacts", e);
        }
    }

    /**
     * Сохраняет скриншот
     */
    private void saveScreenshot(Path dir, String baseName) {
        try {
            AndroidDriver driver = DriverManager.getDriver();
            if (driver == null) {
                log.warn("Driver is null, cannot take screenshot");
                return;
            }

            File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            Path targetPath = dir.resolve(baseName + "_screen.png");
            Files.copy(screenshot.toPath(), targetPath);

            log.debug("Screenshot saved: {}", targetPath);

        } catch (Exception e) {
            log.error("Failed to save screenshot", e);
            throw new AutomationException("Failed to save screenshot", e);
        }
    }

    /**
     * Сохраняет UI иерархию (структуру экрана в XML)
     */
    private void saveUiHierarchy(Path dir, String baseName) {
        try {
            AndroidDriver driver = DriverManager.getDriver();
            if (driver == null) {
                log.warn("Driver is null, cannot get UI hierarchy");
                return;
            }

            String pageSource = driver.getPageSource();
            Path targetPath = dir.resolve(baseName + "_hierarchy.xml");
            Files.writeString(targetPath, pageSource);

            log.debug("UI hierarchy saved: {}", targetPath);

        } catch (Exception e) {
            log.error("Failed to save UI hierarchy", e);
            throw new AutomationException("Failed to save UI hierarchy", e);
        }
    }

    /**
     * Сохраняет стектрейс ошибки
     */
    private void saveErrorStackTrace(Path dir, String baseName, Throwable throwable) {
        try {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            throwable.printStackTrace(pw);
            String stackTrace = sw.toString();

            Path targetPath = dir.resolve(baseName + "_error.txt");
            Files.writeString(targetPath, stackTrace);

            log.debug("Error stacktrace saved: {}", targetPath);

        } catch (Exception e) {
            log.error("Failed to save error stacktrace", e);
            throw new AutomationException("Failed to save error stacktrace", e);
        }
    }

    /**
     * Получает теги из аннотаций теста
     */
    private String getTestTags(ExtensionContext context) {
        try {
            // Проверяем свои аннотации
            var method = context.getRequiredTestMethod();

            if (method.isAnnotationPresent(ru.shilaev.automation.core.annotations.Smoke.class)) {
                return "smoke";
            }
            if (method.isAnnotationPresent(ru.shilaev.automation.core.annotations.Regression.class)) {
                return "regression";
            }
            if (method.isAnnotationPresent(ru.shilaev.automation.core.annotations.Sanity.class)) {
                return "sanity";
            }
            if (method.isAnnotationPresent(ru.shilaev.automation.core.annotations.Integration.class)) {
                return "integration";
            }
            if (method.isAnnotationPresent(ru.shilaev.automation.core.annotations.E2E.class)) {
                return "e2e";
            }
            if (method.isAnnotationPresent(ru.shilaev.automation.core.annotations.Performance.class)) {
                return "performance";
            }
            if (method.isAnnotationPresent(ru.shilaev.automation.core.annotations.NetworkFailure.class)) {
                return "network-failure";
            }
            if (method.isAnnotationPresent(ru.shilaev.automation.core.annotations.AndroidOnly.class)) {
                return "android";
            }
            return "unknown";
        } catch (Exception e) {
            log.warn("Failed to get test tags", e);
            return "unknown";
        }
    }
}