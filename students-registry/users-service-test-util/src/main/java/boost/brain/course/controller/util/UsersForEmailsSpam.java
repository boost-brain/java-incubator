package boost.brain.course.controller.util;

import boost.brain.course.common.users.UserDto;
import lombok.extern.java.Log;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.RecursiveTask;

import static boost.brain.course.controller.Constants.*;

@Log
public class UsersForEmailsSpam extends RecursiveTask<Boolean> {
    private Long count;
    private String url;
    private RestTemplate restTemplate;
    private Random random = new Random();

    public UsersForEmailsSpam() {
    }

    public UsersForEmailsSpam(String url, Long count, RestTemplate restTemplate) {
        this.count = count;
        this.url = url;
        this.restTemplate = restTemplate;
    }

    @Override
    protected Boolean compute() {
        if (count / 2 > 0) {
            if(count % 2 == 1){
                executeUsersForEmails();
            }
            UsersForEmailsSpam usersForEmailsSpamOne = new UsersForEmailsSpam(url, count / 2, restTemplate);
            usersForEmailsSpamOne.fork();
            UsersForEmailsSpam usersForEmailsSpamTwo = new UsersForEmailsSpam(url, count / 2, restTemplate);
            usersForEmailsSpamTwo.fork();
        } else {
            executeUsersForEmails();
            return  true;
        }
        return  true;
    }

    private void executeUsersForEmails() {
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

            List<String> emails = new ArrayList<>();
            for (UserDto userDto: usersPage) {
                emails.add(userDto.getEmail());
            }
            HttpEntity<List<String>> request = new HttpEntity<>(emails);
            ResponseEntity<List<UserDto>> responseEntityUsersForEmails = restTemplate.exchange(
                    url + USERS_CONTROLLER_PREFIX + "/" + USERS_FOR_EMAILS_PREFIX,
                    HttpMethod.POST,
                    request,
                    new ParameterizedTypeReference<List<UserDto>>(){});
            List<UserDto> usersUsersForEmails = responseEntityUsersForEmails.getBody();

            if (usersUsersForEmails == null || usersUsersForEmails.isEmpty())
                throw new RuntimeException("Error! responseEntityUsersForEmails = null or empty!");

            for (UserDto userDto: usersUsersForEmails) {
                if (!emails.contains(userDto.getEmail())) {
                    throw new RuntimeException("Error! usersPage != usersUsersForEmails");
                }
            }
            log.info("executeUsersForEmails time :" + (System.currentTimeMillis() - startTime) + "ms " );
        } catch (Exception e) {
            log.severe(e.getMessage());
            e.printStackTrace();
        }
    }
}
