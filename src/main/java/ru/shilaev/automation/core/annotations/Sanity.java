package ru.shilaev.automation.core.annotations;

import org.junit.jupiter.api.Tag;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Санитарные тесты (sanity tests).
 * <p>
 * Тесты, проверяющие новую функциональность перед запуском полной регрессии:
 * <ul>
 *     <li>Работает ли новая фича в базовом сценарии?</li>
 *     <li>Не сломала ли новая фича критический функционал?</li>
 * </ul>
 * </p>
 * <p>
 * <b>Когда использовать:</b><br>
 * Запускать после добавления новой функциональности, перед регрессией.
 * </p>
 * <p>
 * <b>Запуск:</b><br>
 * {@code ./gradlew test -Dtags=sanity}
 * </p>
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Tag("sanity")
public @interface Sanity {
}