package boost.brain.course.projects.controller.util;

import boost.brain.course.common.users.UserDto;
import lombok.extern.java.Log;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Random;
import java.util.concurrent.RecursiveTask;

import static boost.brain.course.projects.controller.Constants.*;

@Log
public class DeleteSpam extends RecursiveTask<Boolean> {
    private Long count;
    private String url;
    private RestTemplate restTemplate;
    private Random random = new Random();

    public DeleteSpam() {
    }

    public DeleteSpam(String url, Long count, RestTemplate restTemplate) {
        this.count = count;
        this.restTemplate = restTemplate;
        this.url = url;
    }

    @Override
    protected Boolean compute() {
        if (count / 2 > 0) {
            if(count % 2 == 1){
                executeDelete();
            }
            DeleteSpam deleteSpamOne = new DeleteSpam(url, count / 2, restTemplate);
            deleteSpamOne.fork();
            DeleteSpam deleteSpamTwo = new DeleteSpam(url, count / 2, restTemplate);
            deleteSpamTwo.fork();
        } else {
            executeDelete();
            return  true;
        }
        return  true;
    }

    private void executeDelete() {
        int randomPage = random.nextInt(1000);
        long startTime = System.currentTimeMillis();

        ResponseEntity<List<UserDto>> responseEntity = restTemplate.exchange(url + USERS_CONTROLLER_PREFIX + "/" + PAGE_PREFIX + "/" + randomPage +"/20" ,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<UserDto>>(){});

        long stopTime = System.currentTimeMillis();
        long elapsedTime = stopTime - startTime;
        log.info("Page " + randomPage + " time :" + elapsedTime + "ms " );
        List<UserDto> users = responseEntity.getBody();
        String email = "";
        for (UserDto user: users) {
            email = user.getEmail();
            startTime = System.currentTimeMillis();
            try {
                restTemplate.delete(url + USERS_CONTROLLER_PREFIX + "/" + DELETE_PREFIX + "/" + email);
            }catch (Exception e){
                log.info(e.getLocalizedMessage());
            }

            stopTime = System.currentTimeMillis();
            elapsedTime = stopTime - startTime;
            log.info("Delete " + email + " time :" + elapsedTime + "ms " );
        }
    }

}


