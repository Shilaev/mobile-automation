package ru.shilaev.automation.core.listeners;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestExecutionExceptionHandler;
import org.junit.jupiter.api.extension.TestWatcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

/**
 * Логирование начала/конца теста
 */
public class TestLoggerListener implements TestWatcher, TestExecutionExceptionHandler {
    private final static Logger log = LoggerFactory.getLogger(TestLoggerListener.class);

    @Override public void testSuccessful(ExtensionContext context) {
        String testName = getTestName(context);
        log.info("✅ TEST PASSED: {}", testName);
    }

    @Override public void testAborted(ExtensionContext context, Throwable cause) {
        String testName = getTestName(context);
        log.warn("⏭️ TEST SKIPPED: {}", testName);
    }

    @Override public void testDisabled(ExtensionContext context, Optional<String> reason) {
        String testName = getTestName(context);
        log.warn("⚠️ TEST DISABLED: {}", testName);
    }

    @Override
    public void handleTestExecutionException(ExtensionContext context, Throwable throwable) throws Throwable {
        String testName = getTestName(context);
        log.error("❌ TEST FAILED: {}", testName);
        log.error("    Reason: {}", throwable.getMessage());

        throw throwable;
    }

    private String getTestName(ExtensionContext context) {
        String className = context.getRequiredTestClass().getSimpleName();
        String methodName = context.getRequiredTestMethod().getName();
        return className + "." + methodName;
    }
}
