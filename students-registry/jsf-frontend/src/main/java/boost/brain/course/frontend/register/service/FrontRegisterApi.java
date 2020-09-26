package boost.brain.course.frontend.register.service;

import boost.brain.course.common.register.UserRegDto;
import boost.brain.course.common.users.UserDto;
import lombok.Data;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.inject.Named;

@Named
@Service
@Data
@Log
public class FrontRegisterApi {

    @Value("${register-service-url}")
    private String registerUrl;

    private RestTemplate template = new RestTemplate();

    private String name;
    private String email;
    private String password;
    private String gitHubId;
    private int hours;

    public String addAccount(){

        String url = this.getRegisterUrl() + "/create";

        UserRegDto userRegDto = new UserRegDto(this.email, this.name, this.gitHubId, this.hours, this.password);

        UserDto userDto = template.postForObject(url, userRegDto, UserDto.class);
        if (userDto != null) {
            return "confirm-message";
        } else {
            return "email-confirmed";
        }
    }
}
