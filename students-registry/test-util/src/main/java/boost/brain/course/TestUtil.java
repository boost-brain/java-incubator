package boost.brain.course;

import boost.brain.course.common.auth.Credentials;
import boost.brain.course.common.auth.Session;
import lombok.extern.java.Log;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import java.util.UUID;

@Log
public class TestUtil {
    public static void main(String[] args) {
        String credentialsUrl = "http://localhost:8081/api/credentials";
        String loginUrl = "http://localhost:8081/api/login";

        RestTemplateBuilder restTemplateBuilder = new RestTemplateBuilder();
        restTemplateBuilder.additionalMessageConverters(new MappingJackson2HttpMessageConverter());
        RestTemplate restTemplate = restTemplateBuilder.build();

        Credentials credentials = new Credentials();
        HttpEntity<Credentials> credentialsRequest = new HttpEntity<>(credentials);
        String uuid = UUID.randomUUID().toString();

        int countCycles = 10;
        for (int i = 0; i < countCycles; i++) {
            credentials.setLogin(uuid + i + "@testmail.ru");
            credentials.setPassword(credentials.getLogin());


            Boolean result = restTemplate.postForObject(credentialsUrl + "/create", credentialsRequest, Boolean.class);

            if (result == null || !result) {
                log.severe("Error creating record");
                continue;
            }

            Session session = restTemplate.postForObject(loginUrl + "/login", credentialsRequest, Session.class);
            if (session == null || StringUtils.isEmpty(session.getSessionId())) {
                log.severe("Error login");
                continue;
            }

            result = restTemplate.getForObject(loginUrl + "/check/" + session.getSessionId(), Boolean.class);
            if (result == null || !result) {
                log.severe("Error check");
                continue;
            }

            result = restTemplate.getForObject(loginUrl + "/logout/" + session.getSessionId(), Boolean.class);
            if (result == null || !result) {
                log.severe("Error logout");
                continue;
            }

            result = restTemplate.getForObject(loginUrl + "/check/" + session.getSessionId(), Boolean.class);
            if (result == null || result) {
                log.severe("Error check after logout");
                continue;
            }

            try {
                restTemplate.delete(credentialsUrl + "/delete/" + credentials.getLogin());
            }catch (Exception e){
                log.severe(e.getLocalizedMessage());
            }

            log.info("Success cycle №" + (i + 1) + " of " + countCycles);

        }

        log.info("Finish!");
    }
}
