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
public class ReadSpam extends RecursiveTask<Boolean> {
    private Long count;
    private String url;
    private RestTemplate restTemplate;
    private Random random = new Random();

    public ReadSpam() {
    }

    public ReadSpam(String url, Long count, RestTemplate restTemplate) {
        this.count = count;
        this.restTemplate = restTemplate;
        this.url = url;
    }

    @Override
    protected Boolean compute() {
        if (count / 2 > 0) {
            if(count % 2 == 1){
                executeRead();
            }
            ReadSpam readSpamOne = new ReadSpam(url, count / 2, restTemplate);
            readSpamOne.fork();
            ReadSpam readSpamTwo = new ReadSpam(url, count / 2, restTemplate);
            readSpamTwo.fork();
        } else {
            executeRead();
            return  true;
        }
        return  true;
    }

    private void executeRead() {
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
            UserDto readUser = restTemplate.getForObject(url + USERS_CONTROLLER_PREFIX + "/" + READ_PREFIX + "/" + email, UserDto.class);

            stopTime = System.currentTimeMillis();
            elapsedTime = stopTime - startTime;
            log.info("Read " + readUser.getEmail() + " time :" + elapsedTime + "ms " );
        }
    }

}


