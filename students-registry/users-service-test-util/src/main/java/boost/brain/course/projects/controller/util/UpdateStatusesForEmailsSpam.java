package boost.brain.course.projects.controller.util;
import boost.brain.course.common.users.UserDto;
import boost.brain.course.common.users.UserStatus;
import lombok.extern.java.Log;
import org.apache.http.HttpStatus;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.concurrent.RecursiveTask;

import static boost.brain.course.projects.controller.Constants.*;

@Log
public class UpdateStatusesForEmailsSpam extends RecursiveTask<Boolean> {
    private Long count;
    private String url;
    private RestTemplate restTemplate;
    private Random random = new Random();

    public UpdateStatusesForEmailsSpam() {
    }

    public UpdateStatusesForEmailsSpam(String url, Long count, RestTemplate restTemplate) {
        this.count = count;
        this.url = url;
        this.restTemplate = restTemplate;
    }

    @Override
    protected Boolean compute() {
        if (count / 2 > 0) {
            if(count % 2 == 1){
                executeUpdateStatusesForEmails();
            }
            UpdateStatusesForEmailsSpam updateStatusesForEmailsSpamOne = new UpdateStatusesForEmailsSpam(url, count / 2, restTemplate);
            updateStatusesForEmailsSpamOne.fork();
            UpdateStatusesForEmailsSpam updateStatusesForEmailsSpamTwo = new UpdateStatusesForEmailsSpam(url, count / 2, restTemplate);
            updateStatusesForEmailsSpamTwo.fork();
        } else {
            executeUpdateStatusesForEmails();
            return  true;
        }
        return  true;
    }

    private void executeUpdateStatusesForEmails() {
        int randomPage = random.nextInt(1000);
        long startTime = System.currentTimeMillis();

        try {
            ResponseEntity<List<UserDto>> responseEntityPage = restTemplate.exchange(
                    url + USERS_CONTROLLER_PREFIX + "/" + PAGE_PREFIX + "/" + randomPage +"/20" ,
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<List<UserDto>>(){});
            List<UserDto> usersPage = responseEntityPage.getBody();
            if (usersPage == null || usersPage.isEmpty()) {
                throw new RuntimeException("Error! usersPage = null or empty!");
            }
            Map<String, UserStatus> emailsWithStatusesMap = new HashMap<>();
            for (UserDto userDto: usersPage) {
                emailsWithStatusesMap.put(userDto.getEmail(), UserStatus.ACADEMIC_LEAVE);
            }
            HttpEntity<Map<String, UserStatus>> request = new HttpEntity<>(emailsWithStatusesMap);
            ResponseEntity<String> response = restTemplate.postForEntity(
                    url + USERS_CONTROLLER_PREFIX + "/" + UPDATE_STATUSES_FOR_EMAILS_PREFIX,
                    request,
                    String.class);
            if (response.getStatusCodeValue() != HttpStatus.SC_OK) {
                throw new RuntimeException("Error! response = null or != OK!");
            }
            log.info("executeUpdateStatusesForEmails time :" + (System.currentTimeMillis() - startTime) + "ms " );
        } catch (Exception e) {
            log.severe(e.getMessage());
            e.printStackTrace();
        }
    }
}
