package boost.brain.course.users.test.util;

import boost.brain.course.users.test.Constants;
import lombok.extern.java.Log;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.RecursiveTask;

@Log
public class CountSpam extends RecursiveTask<Boolean> {
    private Long count;
    private String url;
    private RestTemplate restTemplate;

    public CountSpam() {
    }

    public CountSpam(String url, Long count, RestTemplate restTemplate) {
        this.count = count;
        this.restTemplate = restTemplate;
        this.url = url;
    }

    @Override
    protected Boolean compute() {
        if (count / 2 > 0) {
            if(count % 2 == 1){
                executeCount();
            }
            CountSpam countSpamOne = new CountSpam(url, count / 2, restTemplate);
            countSpamOne.fork();
            CountSpam countSpamTwo = new CountSpam(url, count / 2, restTemplate);
            countSpamTwo.fork();
        } else {
            executeCount();
            return  true;
        }
        return  true;
    }

    private void executeCount() {
        long startTime = System.currentTimeMillis();

        Long c = restTemplate.getForObject(url + Constants.USERS_CONTROLLER_PREFIX + "/" + Constants.COUNT_PREFIX, Long.class);
        long stopTime = System.currentTimeMillis();
        long elapsedTime = stopTime - startTime;
        log.info("Count execute time :" + elapsedTime + "ms");
    }
}


