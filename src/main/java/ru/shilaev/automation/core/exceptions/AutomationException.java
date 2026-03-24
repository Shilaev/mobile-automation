package ru.shilaev.automation.core.exceptions;

/**
 * Базовое исключение для всего тестового фреймворка
 */
public class AutomationException extends RuntimeException {
    public AutomationException(String message) {
        super(message);
    }

  public AutomationException(String message, Throwable cause) {
    super(message, cause);
  }
}
