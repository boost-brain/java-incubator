package boost.brain.course.controller.util;

import boost.brain.course.common.users.UserStatus;
import boost.brain.course.controller.Constants;
import boost.brain.course.common.users.UserDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.java.Log;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Random;
import java.util.concurrent.RecursiveTask;

import static boost.brain.course.controller.Constants.*;

@Log
public class UpdateSpam extends RecursiveTask<Boolean> {
    private Long count;
    private String url;
    private RestTemplate restTemplate;
    private Random random = new Random();

    public UpdateSpam() {
    }

    public UpdateSpam(String url, Long count, RestTemplate restTemplate) {
        this.count = count;
        this.restTemplate = restTemplate;
        this.url = url;
    }

    @Override
    protected Boolean compute() {
        if (count / 2 > 0) {
            if(count % 2 == 1){
                executeUpdate();
            }
            UpdateSpam updateSpamOne = new UpdateSpam(url, count / 2, restTemplate);
            updateSpamOne.fork();
            UpdateSpam updateSpamTwo = new UpdateSpam(url, count / 2, restTemplate);
            updateSpamTwo.fork();
        } else {
            executeUpdate();
            return  true;
        }
        return  true;
    }

    private void executeUpdate() {
        int randomPage = random.nextInt(1000);
        long startTime = System.currentTimeMillis();

        ResponseEntity<List<UserDto>> responseEntity = restTemplate.exchange(url + Constants.USERS_CONTROLLER_PREFIX + "/" + Constants.PAGE_PREFIX + "/" + randomPage +"/20" ,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<UserDto>>(){});

        long stopTime = System.currentTimeMillis();
        long elapsedTime = stopTime - startTime;
        log.info("Page " + randomPage + " time :" + elapsedTime + "ms " );
        List<UserDto> users = responseEntity.getBody();
        String email = "";
        for (UserDto user: users) {
            String generatedName = generateName();
            user.setName(generatedName);
            user.setHours(random.nextInt(10) + 1);
            user.setStatus(UserStatus.TEMPORARILY_INACTIVE);
            startTime = System.currentTimeMillis();

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            ObjectMapper objectMapper = new ObjectMapper();
            String patchInJson = null;
            try {
                patchInJson = objectMapper.writeValueAsString(user);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
            HttpEntity<String> entity = new HttpEntity<>(patchInJson, headers);

            ResponseEntity<String> response = restTemplate.exchange(
                    url + USERS_CONTROLLER_PREFIX + "/" + UPDATE_PREFIX,
                    HttpMethod.PATCH,
                    new HttpEntity<>(user),
                    String.class);

            stopTime = System.currentTimeMillis();
            elapsedTime = stopTime - startTime;
            log.info("Update " + user.getEmail() + " time :" + elapsedTime + "ms " );
        }
    }

    private String generateName(){
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < 25; i++) {
            stringBuilder.append(getLetter());
        }

        return "test-user-" + stringBuilder.toString();
    }

    private char getLetter(){
        int count = ALFA.length();
        int randomI = random.nextInt(count) ;
        return ALFA.charAt(randomI);
    }
}


