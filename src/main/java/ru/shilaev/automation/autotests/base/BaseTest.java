package ru.shilaev.automation.autotests.base;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import ru.shilaev.automation.core.listeners.RetryListener;
import ru.shilaev.automation.core.listeners.ScreenshotListener;
import ru.shilaev.automation.core.listeners.TestLoggerListener;

@ExtendWith({TestLoggerListener.class, RetryListener.class, ScreenshotListener.class})
public class BaseTest extends BasePage {
    @Test
    protected void startApp(){

    }
}
