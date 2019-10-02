package boost.brain.course.tasks;

import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/***
 * Собственная аннотация заменяющая
 * * @ContextConfiguration(initializers = EmbeddedPostgresInitializer.class)
 * любой тест, аннотированный @EmbeddedPostgrestTest запустит базу на случайном порту
 * и со случайным именем, настроит Spring на подключение к этой базе и в конце теста остановит ее.
 * А так же выберем актвный профиль- test
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@ActiveProfiles("test")
@ContextConfiguration(initializers = EmbeddedPostgresInitializer.class)
@interface EmbeddedPostgresTest {
}