package ru.shilaev.automation.core.exceptions;

/**
 * Ошибки элементов (не найден, не кликабелен)
 */
public class ElementException extends RuntimeException {
    private String selector;

    public ElementException(String selector, String message) {
        super(String.format("Element '%s': %s", selector, message));
    }

    public String getSelector() {
        return selector;
    }
}
