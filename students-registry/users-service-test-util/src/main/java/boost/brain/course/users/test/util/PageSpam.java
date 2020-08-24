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
public class PageSpam extends RecursiveTask<Boolean> {
    private Long count;
    private String url;
    private RestTemplate restTemplate;
    private Random random = new Random();

    public PageSpam() {
    }

    public PageSpam(String url, Long count, RestTemplate restTemplate) {
        this.count = count;
        this.restTemplate = restTemplate;
        this.url = url;
    }

    @Override
    protected Boolean compute() {
        if (count / 2 > 0) {
            if(count % 2 == 1){
                executePage();
            }
            PageSpam pageSpamOne = new PageSpam(url, count / 2, restTemplate);
            pageSpamOne.fork();
            PageSpam pageSpamTwo = new PageSpam(url, count / 2, restTemplate);
            pageSpamTwo.fork();
        } else {
            executePage();
            return  true;
        }
        return  true;
    }

    private void executePage() {
        int randomPage = random.nextInt(1000);
        long startTime = System.currentTimeMillis();

        ResponseEntity<List<UserDto>> responseEntity = restTemplate.exchange(url + USERS_CONTROLLER_PREFIX + "/" + PAGE_PREFIX + "/" + randomPage +"/20" ,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<UserDto>>(){});
//        List<UserDto> Users = responseEntity.getBody();
        long stopTime = System.currentTimeMillis();
        long elapsedTime = stopTime - startTime;
        log.info("Page " + randomPage + " time :" + elapsedTime + "ms " );

    }

}


