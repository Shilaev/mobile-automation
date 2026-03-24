package ru.shilaev.automation.core.annotations;

import org.junit.jupiter.api.Tag;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Дымовые тесты (smoke tests).
 * <p>
 * Быстрые тесты, проверяющие критический функционал:
 * <ul>
 *     <li>Запускается ли приложение?</li>
 *     <li>Можно ли залогиниться?</li>
 *     <li>Открывается ли главный экран?</li>
 *     <li>Работает ли основной сценарий?</li>
 * </ul>
 * </p>
 * <p>
 * <b>Когда использовать:</b><br>
 * Запускать перед каждым пулл-реквестом или деплоем на стейджинг.
 * </p>
 * <p>
 * <b>Запуск:</b><br>
 * {@code ./gradlew test -Dtags=smoke}
 * </p>
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Tag("smoke")
public @interface Smoke {
}