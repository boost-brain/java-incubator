package boost.brain.course.frontend.register.service;

import boost.brain.course.frontend.register.model.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.inject.Named;

@Named
@Service
public class FrontRegisterApi {

    @Value("${url.host}")
    private String host;

    @Value("${url.port.register}")
    private String port;

    @Value("${url.path.register}")
    private String path;

    private RestTemplate template = new RestTemplate();

    public String addAccount(String name, String email, String password, String gitHabId, int hours){
        String url = this.host + ":" + this.port + this.path + "/create";

        User user = new User(name, email, password, gitHabId, hours);
        User result = template.postForObject(url, user, User.class);

        if (result != null){
            return "confirm-message";
        }else{
            return "email-confirmed";
        }
    }
}
