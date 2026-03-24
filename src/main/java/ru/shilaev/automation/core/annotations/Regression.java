package ru.shilaev.automation.core.annotations;

import org.junit.jupiter.api.Tag;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Регрессионные тесты (regression tests).
 * <p>
 * Полный набор тестов, проверяющих, что ничего не сломалось после изменений:
 * <ul>
 *     <li>Все функциональные тесты</li>
 *     <li>Пограничные случаи</li>
 *     <li>Интеграционные сценарии</li>
 *     <li>Пользовательские сценарии</li>
 * </ul>
 * </p>
 * <p>
 * <b>Когда использовать:</b><br>
 * Запускать перед релизом, в ночных CI-запусках или после крупных изменений.
 * </p>
 * <p>
 * <b>Запуск:</b><br>
 * {@code ./gradlew test -Dtags=regression}
 * </p>
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Tag("regression")
public @interface Regression {
}