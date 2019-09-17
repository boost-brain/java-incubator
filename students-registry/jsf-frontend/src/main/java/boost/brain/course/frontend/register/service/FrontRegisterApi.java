package boost.brain.course.frontend.register.service;

import boost.brain.course.frontend.register.model.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.inject.Named;

@Named
@Service
public class FrontRegisterApi {

    @Value("${register-service-url}")
    private String url;
    private RestTemplate template = new RestTemplate();

    public User addAccount(String name, String email, String password, String gitHubId, int hours){
        String createUrl = url + "/create";

        User user = new User(name, email, password, gitHubId, hours);
        return template.postForObject(createUrl, user, User.class);
    }
}
