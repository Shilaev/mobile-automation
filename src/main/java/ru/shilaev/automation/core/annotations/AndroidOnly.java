package ru.shilaev.automation.core.annotations;

import org.junit.jupiter.api.Tag;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Тесты, работающие только на Android.
 * <p>
 * Тесты, которые проверяют Android-специфичный функционал:
 * <ul>
 *     <li>Push-уведомления</li>
 *     <li>Физическая кнопка "Назад"</li>
 *     <li>Разрешения (Android специфичные)</li>
 *     <li>Google сервисы</li>
 * </ul>
 * </p>
 * <p>
 * <b>Когда использовать:</b><br>
 * Когда тест проверяет функционал, который существует только на Android.
 * </p>
 * <p>
 * <b>Запуск:</b><br>
 * {@code ./gradlew test -Dtags=android}
 * </p>
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Tag("android")
public @interface AndroidOnly {
}