package boost.brain.course.users.test.util;

import boost.brain.course.common.users.UserDto;
import boost.brain.course.common.users.UserStatus;
import lombok.extern.java.Log;
import org.apache.http.HttpStatus;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Random;
import java.util.concurrent.RecursiveTask;

import static boost.brain.course.users.test.Constants.*;

@Log
public class UpdateStatusSpam extends RecursiveTask<Boolean> {
    private Long count;
    private String url;
    private RestTemplate restTemplate;
    private Random random = new Random();

    public UpdateStatusSpam() {
    }

    public UpdateStatusSpam(String url, Long count, RestTemplate restTemplate) {
        this.count = count;
        this.url = url;
        this.restTemplate = restTemplate;
    }

    @Override
    protected Boolean compute() {
        if (count / 2 > 0) {
            if(count % 2 == 1){
                executeUpdateStatus();
            }
            UpdateStatusSpam updateStatusSpamOne = new UpdateStatusSpam(url, count / 2, restTemplate);
            updateStatusSpamOne.fork();
            UpdateStatusSpam updateStatusSpamTwo = new UpdateStatusSpam(url, count / 2, restTemplate);
            updateStatusSpamTwo.fork();
        } else {
            executeUpdateStatus();
            return  true;
        }
        return  true;
    }

    private void executeUpdateStatus() {
        int randomPage = random.nextInt(1000);
        long startTime = System.currentTimeMillis();

        try {
            ResponseEntity<List<UserDto>> responseEntityPage = restTemplate.exchange(
                    url + USERS_CONTROLLER_PREFIX + "/" + PAGE_PREFIX + "/" + randomPage +"/20" ,
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<List<UserDto>>(){});
            List<UserDto> usersPage = responseEntityPage.getBody();

            if (usersPage == null || usersPage.isEmpty()) throw new RuntimeException("Error! usersPage = null or empty!");

            for (UserDto userDto: usersPage) {
                HttpEntity<UserStatus> request = new HttpEntity<>(UserStatus.ACADEMIC_LEAVE);
                ResponseEntity<String> response = restTemplate.postForEntity(
                        url + USERS_CONTROLLER_PREFIX + "/" + UPDATE_STATUS_PREFIX + "/" + userDto.getEmail(),
                        request,
                        String.class);
                if (response.getStatusCodeValue() != HttpStatus.SC_OK) {
                    throw new RuntimeException("Error! response = null or != OK!");
                }
            }
            log.info("executeUpdateStatus time :" + (System.currentTimeMillis() - startTime) + "ms " );
        } catch (Exception e) {
            log.severe(e.getMessage());
            e.printStackTrace();
        }
    }
}
