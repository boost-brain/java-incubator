package boost.brain.course.tasks;


import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.support.GenericApplicationContext;
import ru.yandex.qatools.embed.postgresql.EmbeddedPostgres;

import java.io.IOException;

import static ru.yandex.qatools.embed.postgresql.distribution.Version.Main.V9_6;

/***
 * при создании нового контекста приложения запускает базу на случайном порту и в контекст передаем данные подключения.
 * В качестве базы выбран embeddedPostgres от Yandex (смотри pom.xml)
 */
public class EmbeddedPostgresInitializer
        implements ApplicationContextInitializer<GenericApplicationContext> {

    @Override
    public void initialize(GenericApplicationContext applicationContext) {
        final EmbeddedPostgres postgres = new EmbeddedPostgres(V9_6);
        try {
            final String url = postgres.start();
            TestPropertyValues values = TestPropertyValues.of(
                    "spring.test.database.replace=none",
                    "spring.datasource.url=" + url,
                    "spring.datasource.driver-class-name=org.postgresql.Driver",
                    "spring.jpa.hibernate.ddl-auto=create",
                    "spring.jpa.properties.hibernate.jdbc.lob.non_contextual_creation=true");

            values.applyTo(applicationContext);
            applicationContext.registerBean(EmbeddedPostgres.class, () -> postgres,
                    beanDefinition -> beanDefinition.setDestroyMethodName("stop"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}