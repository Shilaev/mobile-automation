package ru.shilaev.automation.core.annotations;

import org.junit.jupiter.api.Tag;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Тесты при сбоях сети (network failure tests).
 * <p>
 * Тесты, проверяющие поведение при проблемах с интернетом:
 * <ul>
 *     <li>Потеря интернета во время работы</li>
 *     <li>Медленное соединение (2G, 3G)</li>
 *     <li>Оффлайн-режим</li>
 *     <li>Retry-логика при ошибках</li>
 * </ul>
 * </p>
 * <p>
 * <b>Когда использовать:</b><br>
 * Запускать периодически, особенно если меняется сетевая логика.
 * </p>
 * <p>
 * <b>Запуск:</b><br>
 * {@code ./gradlew test -Dtags=network-failure}
 * </p>
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Tag("network-failure")
public @interface NetworkFailure {
}