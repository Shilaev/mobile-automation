package ru.shilaev.automation.core.listeners;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestExecutionExceptionHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * Перезапуск упавших тестов
 */
public class RetryListener implements TestExecutionExceptionHandler {
    private final static Logger log = LoggerFactory.getLogger(RetryListener.class);
    private final static int MAX_RETRIES = 3;
    private final static Map<String, Integer> retryCount = new HashMap<>();

    @Override public void handleTestExecutionException(ExtensionContext extensionContext, Throwable throwable) throws Throwable {
        String testName = getTestName(extensionContext);
        int attempts = retryCount.getOrDefault(testName, 0);

        if (attempts < MAX_RETRIES) {
            attempts += 1;
            retryCount.put(testName, attempts);
            log.warn("⚠️ Test failed. Retry {}/{} for: {}", attempts, MAX_RETRIES, testName);
            return;
        } else {
            log.error("❌ TEST FAILED: {}", testName);
            log.error("   Reason: {}", throwable.getMessage());

            throw throwable;
        }
    }

    private String getTestName(ExtensionContext extensionContext) {
        return extensionContext.getRequiredTestClass().getSimpleName() + "." +
                extensionContext.getRequiredTestMethod().getName();
    }
}
