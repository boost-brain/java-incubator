package boost.brain.course.users.test.util;
import boost.brain.course.common.users.UserDto;
import lombok.extern.java.Log;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Random;
import java.util.concurrent.RecursiveTask;

import static boost.brain.course.users.test.Constants.*;

@Log
public class CheckIfExistsSpam extends RecursiveTask<Boolean> {
    private Long count;
    private String url;
    private RestTemplate restTemplate;
    private Random random = new Random();

    public CheckIfExistsSpam() {
    }

    public CheckIfExistsSpam(String url, Long count, RestTemplate restTemplate) {
        this.count = count;
        this.url = url;
        this.restTemplate = restTemplate;
    }

    @Override
    protected Boolean compute() {
        if (count / 2 > 0) {
            if(count % 2 == 1){
                executeCheckIfExists();
            }
            CheckIfExistsSpam checkIfExistsSpamOne = new CheckIfExistsSpam(url, count / 2, restTemplate);
            checkIfExistsSpamOne.fork();
            CheckIfExistsSpam checkIfExistsSpamTwo = new CheckIfExistsSpam(url, count / 2, restTemplate);
            checkIfExistsSpamTwo.fork();
        } else {
            executeCheckIfExists();
            return  true;
        }
        return  true;
    }

    private void executeCheckIfExists() {
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
                Boolean response = restTemplate.getForObject(
                        url + USERS_CONTROLLER_PREFIX + "/" + CHECK_IF_EXISTS_PREFIX + "/" + userDto.getEmail(),
                        Boolean.class);
                if (response == null || !response.booleanValue()) {
                    throw new RuntimeException("Error! response = null or false!");
                }
            }
            log.info("executeUsersForEmails time :" + (System.currentTimeMillis() - startTime) + "ms " );
        } catch (Exception e) {
            log.severe(e.getMessage());
            e.printStackTrace();
        }
    }

}
