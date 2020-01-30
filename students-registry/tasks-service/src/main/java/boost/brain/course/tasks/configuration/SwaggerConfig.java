package boost.brain.course.tasks.configuration;

import boost.brain.course.tasks.controller.TasksController;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.VendorExtension;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collection;
import java.util.Collections;

import static springfox.documentation.builders.PathSelectors.regex;


@Configuration
@EnableSwagger2
@ComponentScan(basePackageClasses = TasksController.class)
public class SwaggerConfig {
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(regex("/api.*"))
                .build()
                .apiInfo(getInfo());
    }


    private ApiInfo getInfo() {
        return new ApiInfo(getTitle(), getDescription(), getVersion(), getTermsOfServiceUrl(), getContact(), getLicense(),
                getLicenseUrl(), getVendorExtension());
    }

    /**
     * Контакты с маэстро
     *
     * @return контакт
     */
    private Contact getContact() {
        return new Contact("Николай Кожокарь BoostBrain- Youtube канал", "https://www.youtube.com/channel/UCcORCILcBXus2e4vWSS1k2Q",
                "");
    }

    private String getLicenseUrl() {
        return "https://www.apache.org/licenses/LICENSE-2.0";
    }

    /**
     * Лицензия
     *
     * @return название лицензии
     */
    private String getLicense() {
        return "Apache license version 2.0";
    }

    /**
     * Условия использования
     *
     * @return ссылка на условия.
     */
    private String getTermsOfServiceUrl() {
        return "https://www.termsfeed.com/terms-conditions/49936e3b831efae50dd38dae32f0e9b2";
    }

    /**
     * Версия сервиса
     *
     * @return версия
     */
    private String getVersion() {
        return "1.0";
    }

    /**
     * текстовое Описание сервиса
     *
     * @return описание
     */
    private String getDescription() {
        return "Микросервис “tasks-service” предназначен для управления и хранения в БД данных Задач и комментарий для них.\n" +
                "Построен на Docker образе openjdk:8-jdk-alpine, с использованием фреймворка SpringBoot, в качестве БД используется внешняя (не входящая в образ) Postgresql база. Для упрощения написания кода используется Lombok. В качестве документирования Swagger (swagger-ui).\n" +
                "Доступ к данным через Api –интерфейс.\n";
    }

    /**
     * Имя сервиса
     *
     * @return Имя
     */
    private String getTitle() {
        return "Сервис управления и хранения задачами (Task), а так же комментариям к ним";
    }

    /**
     * Заглушка
     *
     * @return спсок вендоров
     */
    private Collection<VendorExtension> getVendorExtension() {
        return Collections.singletonList(new VendorExtension() {
            @Override
            public String getName() {
                return "1";
            }

            @Override
            public Object getValue() {
                return "1";
            }
        });
    }
}
