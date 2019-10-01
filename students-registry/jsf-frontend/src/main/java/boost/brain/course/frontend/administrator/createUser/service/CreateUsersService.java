package boost.brain.course.frontend.administrator.createUser.service;

import boost.brain.course.common.auth.Credentials;
import boost.brain.course.common.auth.UserDto;
import boost.brain.course.frontend.register.model.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

import javax.inject.Named;
import java.io.Serializable;

@Named
@Service
public class CreateUsersService implements Serializable {

//    @Value("${users-service-url}")
    private String urlUsers = "http://users-service:8080/api/users/create";
    //private String urlAuth = "http://auth-service:8080/api/credentials/create";

    private RestTemplate template = new RestTemplate();

    @RequestMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public void addNewUser(String name, String email, String password, String gitHabId, int hours) {


        User user = new User(name, email, password, gitHabId, hours);
        UserDto userDto = new UserDto();
        userDto.setEmail(user.getEmail());
        userDto.setGitHabId(user.getGitHabId());
        userDto.setName(user.getName());
        userDto.setHours(user.getHours());
        template.postForObject(urlUsers, userDto, UserDto.class);
//        Credentials credentials = new Credentials(user.getEmail(), user.getPassword());
//        template.postForObject(urlAuth, credentials, Credentials.class);
        String resultMessage;

        if (user != null) {
            resultMessage = "User " + userDto.getName() + " created";
        } else {
            resultMessage = "User " + userDto.getName() + " not created";
        }
    }
}
