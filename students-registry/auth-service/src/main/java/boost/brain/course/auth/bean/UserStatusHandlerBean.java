package boost.brain.course.auth.bean;

import boost.brain.course.auth.Constants;
import boost.brain.course.auth.repository.SessionsRepository;
import boost.brain.course.common.users.UserStatus;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

@Component
@Log
public class UserStatusHandlerBean {

    private SessionsRepository sessionsRepository;
    private RestTemplateBuilder restTemplateBuilder;
    private RestTemplate restTemplate;

    @Value("${users-service-url}")
    private String usersUrl;

    @Autowired
    public UserStatusHandlerBean(SessionsRepository sessionsRepository) {
        this.sessionsRepository = sessionsRepository;
    }

    @PostConstruct
    public void init() {
        log.info("Init UserStatusHandlerBean");
        restTemplateBuilder = new RestTemplateBuilder();
        restTemplateBuilder.additionalMessageConverters(new MappingJackson2HttpMessageConverter());
        restTemplate = restTemplateBuilder.build();
    }

    @Scheduled(fixedDelay = Constants.FIXED_DELAY_UPDATE_STATUSES_FOR_USERS)
    public void scheduleFixedDelayUpdateStatusesForUsers() {
        log.info("Start UserStatusHandlerBean.scheduleFixedDelayUpdateStatusesForUsers");
        Map<String, Long> emailsWithStartTimeMap =  sessionsRepository.getEmailsWithStartTimeMap();
        if(emailsWithStartTimeMap == null || emailsWithStartTimeMap.isEmpty()) {
            log.severe("Stop UserStatusHandlerBean.scheduleFixedDelayUpdateStatusesForUsers!" +
                    " emailsWithStartTimeMap = null or empty!");
            return;
        }
        Map<String, UserStatus> emailsWithStatusesMap = this.getEmailsWithStatusesMap(emailsWithStartTimeMap);
        if (!emailsWithStatusesMap.isEmpty()) {
            this.updateStatusesForUsers(emailsWithStatusesMap);
        } else {
            log.info("emailsWithStatusesMap is empty!");
        }
        log.info("Finish UserStatusHandlerBean.scheduleFixedDelayUpdateStatusesForUsers");
    }

    private Map<String, UserStatus> getEmailsWithStatusesMap(Map<String, Long> emailsWithStartTimeMap) {
        log.info("Start UserStatusHandlerBean.getEmailsWithStatusesMap, size = " + emailsWithStartTimeMap.size());
        Map<String, UserStatus> result = new HashMap<>();
        long currentTime = System.currentTimeMillis();
        for (Map.Entry<String, Long> pair: emailsWithStartTimeMap.entrySet()) {
            if (pair.getValue() < (currentTime - Constants.TIME_DELAY_FOR_ACADEMIC_LEAVE)) {
                result.put(pair.getKey(),UserStatus.ACADEMIC_LEAVE);
            } else if (pair.getValue() < (currentTime - Constants.TIME_DELAY_FOR_TEMPORARILY_INACTIVE)) {
                result.put(pair.getKey(),UserStatus.TEMPORARILY_INACTIVE);
            }
        }
        log.info("Finish UserStatusHandlerBean.getEmailsWithStatusesMap");
        return result;
    }

    private void updateStatusesForUsers(Map<String, UserStatus> emailsWithStatusesMap) {
        log.info("Start UserStatusHandler.updateStatusesForUsers, size = " + emailsWithStatusesMap.size());
        try {
            HttpEntity<Map<String, UserStatus>> request = new HttpEntity<>(emailsWithStatusesMap);
            ResponseEntity<String> response = restTemplate.postForEntity(
                    usersUrl + Constants.UPDATE_STATUSES_FOR_EMAILS_PREFIX,
                    request,
                    String.class);
            log.info("Response status code = " + response.getStatusCode());
        } catch (Exception e) {
            log.severe("UserStatusHandlerBean.updateStatusesForUsers throw exception!");
            e.printStackTrace();
        }
        log.info("Finish UserStatusHandlerBean.updateStatusesForUsers");
    }
}
