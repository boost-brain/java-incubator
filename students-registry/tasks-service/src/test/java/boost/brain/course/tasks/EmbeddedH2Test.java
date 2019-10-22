package boost.brain.course.tasks;


import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.test.context.ActiveProfiles;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * Эта конфигурация ищет в classpath одну из поддерживаемых embedded баз данных и переконфигурирует контекст,
 * чтобы DataSource указывал на случайно созданную in-memory базу. Так как я добавил зависимость на H2 базу —
 * то больше делать ничего не нужно, просто наличие этой аннотации автоматически для каждого запуска теста предоставит
 * пустую базу, и это просто невероятно удобно для тестирования.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@ActiveProfiles("test")
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public @interface EmbeddedH2Test {
}
