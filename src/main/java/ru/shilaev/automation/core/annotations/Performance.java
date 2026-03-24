package ru.shilaev.automation.core.annotations;

import org.junit.jupiter.api.Tag;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Тесты производительности (performance tests).
 * <p>
 * Тесты, проверяющие скорость работы и потребление ресурсов:
 * <ul>
 *     <li>Время загрузки экранов и элементов</li>
 *     <li>FPS при скролле и анимациях</li>
 *     <li>Потребление оперативной памяти</li>
 *     <li>Потребление заряда батареи</li>
 * </ul>
 * </p>
 * <p>
 * <b>Когда использовать:</b><br>
 * Запускать периодически, перед большими релизами.
 * </p>
 * <p>
 * <b>Запуск:</b><br>
 * {@code ./gradlew test -Dtags=performance}
 * </p>
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Tag("performance")
public @interface Performance {
}