package boost.brain.course.tasks.service;

import boost.brain.course.common.users.UserStatus;
import boost.brain.course.tasks.Constants;
import lombok.NoArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


/**
 * модуль взаимодействия с другими Сервисами
 */
@Service
@Log
@NoArgsConstructor
public class OtherServiceCommunication {
    @Value("${users-service-url}")
    private String usersUrl;

    /**
     * метод обновления статуса пользователя
     *
     * @param email  e-mail пользователя
     * @param status Enum serStatus
     */
    public void updateStatusForUser(String email, UserStatus status) {
        log.info("Start TasksController.updateStatusForUser");
        RestTemplateBuilder restTemplateBuilder = new RestTemplateBuilder();
        restTemplateBuilder.additionalMessageConverters(new MappingJackson2HttpMessageConverter());
        RestTemplate restTemplate = restTemplateBuilder.build();
        HttpEntity<UserStatus> request = new HttpEntity<>(status);
        try {
            ResponseEntity<String> response = restTemplate.postForEntity(
                    usersUrl + Constants.UPDATE_STATUS_PREFIX + "/" + email,
                    request,
                    String.class);
            log.info("Response status code = " + response.getStatusCode());
        } catch (Exception e) {
            log.severe("TasksController.updateStatusForUser throw exception!");
            e.printStackTrace();
        }
        log.info("Finish TasksController.updateStatusForUser");
    }
}
