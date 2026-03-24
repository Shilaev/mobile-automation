package ru.shilaev.automation.core.annotations;

import org.junit.jupiter.api.Tag;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Тесты совместимости (compatibility tests).
 * <p>
 * Тесты, проверяющие работу на разных устройствах и версиях ОС:
 * <ul>
 *     <li>Разные версии Android (11, 12, 13, 14)</li>
 *     <li>Разные размеры экранов (телефоны, планшеты)</li>
 *     <li>Разные разрешения (HD, FullHD, 4K)</li>
 *     <li>Разные производители (Samsung, Xiaomi, Pixel)</li>
 * </ul>
 * </p>
 * <p>
 * <b>Когда использовать:</b><br>
 * Запускать перед релизом на всех поддерживаемых устройствах.
 * </p>
 * <p>
 * <b>Запуск:</b><br>
 * {@code ./gradlew test -Dtags=compatibility}
 * </p>
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Tag("compatibility")
public @interface Compatibility {
}