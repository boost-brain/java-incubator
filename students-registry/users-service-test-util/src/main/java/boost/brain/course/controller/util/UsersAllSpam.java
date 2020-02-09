package boost.brain.course.controller.util;
import boost.brain.course.common.users.UserDto;
import lombok.extern.java.Log;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Random;
import java.util.concurrent.RecursiveTask;

import static boost.brain.course.controller.Constants.*;

@Log
public class UsersAllSpam extends RecursiveTask<Boolean> {
    private Long count;
    private String url;
    private RestTemplate restTemplate;

    public UsersAllSpam() {
    }

    public UsersAllSpam(String url, Long count, RestTemplate restTemplate) {
        this.count = count;
        this.url = url;
        this.restTemplate = restTemplate;
    }

    @Override
    protected Boolean compute() {
        if (count / 2 > 0) {
            if(count % 2 == 1){
                executeUsersAll();
            }
            UsersAllSpam usersAllSpamOne = new UsersAllSpam(url, count / 2, restTemplate);
            usersAllSpamOne.fork();
            UsersAllSpam usersAllSpamTwo = new UsersAllSpam(url, count / 2, restTemplate);
            usersAllSpamTwo.fork();
        } else {
            executeUsersAll();
            return  true;
        }
        return  true;
    }

    private void executeUsersAll() {
        long startTime = System.currentTimeMillis();
        try {
            Long countResponse = restTemplate.getForObject(
                    url + USERS_CONTROLLER_PREFIX + "/" + COUNT_PREFIX,
                    Long.class);
            if (countResponse == null || countResponse.longValue() == 0) {
                throw new RuntimeException("Error! countResponse = null or 0!");
            }
            ResponseEntity<List<UserDto>> responseEntityAllUsers = restTemplate.exchange(
                    url + USERS_CONTROLLER_PREFIX + "/" + USERS_ALL_PREFIX,
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<List<UserDto>>(){});
            List<UserDto> allUsers = responseEntityAllUsers.getBody();

            if (allUsers == null || allUsers.isEmpty()) {
                throw new RuntimeException("Error! allUsers = null or empty!");
            }
            if (allUsers.size() != countResponse.longValue()) {
                throw new RuntimeException("Error! allUsers.size != countResponse!");
            }
            log.info("executeUsersForEmails time for "+ countResponse + " users:" + (System.currentTimeMillis() - startTime) + "ms " );
        } catch (Exception e) {
            log.severe(e.getMessage());
            e.printStackTrace();
        }
    }
}
