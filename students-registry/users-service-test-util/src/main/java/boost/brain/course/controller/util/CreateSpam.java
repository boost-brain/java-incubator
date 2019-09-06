package boost.brain.course.controller.util;

import boost.brain.course.controller.dto.UserDto;
import lombok.extern.java.Log;
import org.springframework.web.client.RestTemplate;

import java.util.Random;
import java.util.concurrent.RecursiveTask;

import static boost.brain.course.controller.Constants.*;

@Log
public class CreateSpam extends RecursiveTask<Boolean> {
    private Long count;
    private String url;
    private RestTemplate restTemplate;
    private Random random = new Random();

    public CreateSpam() {
    }

    public CreateSpam(String url, Long count, RestTemplate restTemplate) {
        this.count = count;
        this.restTemplate = restTemplate;
        this.url = url;
    }

    @Override
    protected Boolean compute() {
        if (count / 2 > 0) {
            if (count % 2 == 1) {
                executeCreate();
            }
            CreateSpam createSpamOne = new CreateSpam(url, count / 2, restTemplate);
            createSpamOne.fork();
            CreateSpam createSpamTwo = new CreateSpam(url, count / 2, restTemplate);
            createSpamTwo.fork();
        } else {
            executeCreate();
            return true;
        }
        return true;
    }

    private void executeCreate() {
        UserDto user = new UserDto();
        String genName = generateName();
        user.setName(genName);
        user.setGitHabId(genName);
        user.setEmail(genName + "@mail.ru");
        user.setHours(1);

        long startTime = System.currentTimeMillis();

        String c = restTemplate.postForObject(url + USERS_CONTROLLER_PREFIX + "/" + CREATE_PREFIX, user, String.class);
        long stopTime = System.currentTimeMillis();
        long elapsedTime = stopTime - startTime;
        log.info("Create " + user.getEmail() + " time :" + elapsedTime + "ms ");
    }

    private String generateName() {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < 25; i++) {
            stringBuilder.append(getLetter());
        }

        return stringBuilder.toString();
    }

    private char getLetter() {
        int count = ALFA.length();
        int randomI = random.nextInt(count);
        return ALFA.charAt(randomI);
    }
}


