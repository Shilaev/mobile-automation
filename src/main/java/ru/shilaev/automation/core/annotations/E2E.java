package ru.shilaev.automation.core.annotations;

import org.junit.jupiter.api.Tag;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Сквозные тесты (end-to-end tests).
 * <p>
 * Тесты, проверяющие полные пользовательские сценарии:
 * <ul>
 *     <li>Регистрация → поиск → просмотр → взаимодействие → выход</li>
 *     <li>Загрузка контента → модерация → отображение</li>
 *     <li>Сложные бизнес-сценарии</li>
 * </ul>
 * </p>
 * <p>
 * <b>Когда использовать:</b><br>
 * Запускать перед релизом для проверки критических пользовательских путей.
 * </p>
 * <p>
 * <b>Запуск:</b><br>
 * {@code ./gradlew test -Dtags=e2e}
 * </p>
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Tag("e2e")
public @interface E2E {
}