package ru.shilaev.automation.core.annotations;

import org.junit.jupiter.api.Tag;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Интеграционные тесты (integration tests).
 * <p>
 * Тесты, проверяющие взаимодействие с внешними системами:
 * <ul>
 *     <li>API запросы и ответы</li>
 *     <li>Синхронизация данных с сервером</li>
 *     <li>Работа с базой данных</li>
 *     <li>Загрузка и выгрузка данных</li>
 * </ul>
 * </p>
 * <p>
 * <b>Когда использовать:</b><br>
 * Запускать после изменений в API, перед релизом.
 * </p>
 * <p>
 * <b>Запуск:</b><br>
 * {@code ./gradlew test -Dtags=integration}
 * </p>
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Tag("integration")
public @interface Integration {
}