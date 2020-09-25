package boost.brain.course.auth.cofiguration;

import boost.brain.course.auth.controller.CredentialsController;
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
@ComponentScan(basePackageClasses = CredentialsController.class)
public class SwaggerConfig {
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(regex("/api.*"))
                .build()
                .useDefaultResponseMessages(false)
                .apiInfo(getInfo());
    }


    private ApiInfo getInfo() {
        return new ApiInfo(getTitle(), getDescription(), getVersion(), getTermsOfServiceUrl(), getContact(), getLicense(),
                getLicenseUrl(), getVendorExtension());
    }

    /**
     * Контакты
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
     * Описание микросервиса
     *
     * @return описание
     */
    private String getDescription() {
        return "Микросервис “Auth-service” предназначен для управления входом/выходом пользователей в приложение, валидации существующего подключения по SessionId, " +
                "а так же регистрацией и управлением данных пользователей для входа. Состоит из двух контроллеров.\n" +
                "Собирается в Docker-образ на основе openjdk:8-jdk-alpine, с использованием фреймворка SpringBoot, для хранения данных используется внешняя (не входящая в образ) Postgresql база данных.\n" +
                "Для упрощения написания кода используется Lombok. \n" +
                "Для документирования используется Swagger (swagger-ui).\n" +
                "Работа с микросервисом осуществляется через REST API.\n" +
                "Конфигурация сервиса производится переменными окружения.\n";
    }

    /**
     * Имя сервиса
     *
     * @return Имя
     */
    private String getTitle() {
        return "Сервис управления пользователями и их авторизацией.";
    }

    /**
     * Заглушка
     *
     * @return список вендоров
     */
    private Collection<VendorExtension> getVendorExtension() {
        return Collections.singletonList(new VendorExtension() {
            @Override
            public String getName() {
                return "None";
            }

            @Override
            public Object getValue() {
                return "None";
            }
        });
    }
}
