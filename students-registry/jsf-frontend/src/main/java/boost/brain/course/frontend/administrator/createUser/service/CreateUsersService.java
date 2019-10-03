package boost.brain.course.frontend.administrator.createUser.service;

import boost.brain.course.common.auth.Credentials;
import boost.brain.course.common.auth.UserDto;
import boost.brain.course.frontend.register.model.User;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.annotation.RequestScope;

import javax.inject.Named;
import java.io.Serializable;

@Named
@RequestScope
@Service
public class CreateUsersService implements Serializable {

    private static final long serialVersionUID = 1L;

    private String urlUsers = "http://users-service:8080/api/users/create";
    private String urlAuth = "http://auth-service:8080/api/credentials/create";

    private RestTemplate restTemplate;
    private UserDto userDto = new UserDto();
    private User user;
    private Credentials credentials;
    String resultMessage;

    @RequestMapping(consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public void addNewUser(String name, String email, String password, String gitHabId, int hours) {

        user = new User(name, email, password, gitHabId, hours);
        userDto.setEmail(user.getEmail());
        userDto.setGitHabId(user.getGitHabId());
        userDto.setName(user.getName());
        userDto.setHours(user.getHours());


        try {
            restTemplate = new RestTemplate();
            restTemplate.postForObject(urlUsers, userDto, UserDto.class);
            addNewCredentials(email, password);
        } catch (HttpStatusCodeException ex) {
            System.out.println(ex.getStatusCode());
            ex.printStackTrace();
        }
    }

    @RequestMapping(consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public void addNewCredentials(String email, String password) {
        credentials = new Credentials(email, password);
        try {
            restTemplate = new RestTemplate();
            restTemplate.postForObject(urlAuth, credentials, Boolean.class);
            resultMessage = "User " + userDto.getName() + " created";
        } catch (HttpStatusCodeException ex) {
            resultMessage = "User " + userDto.getName() + " not created";
            System.out.println(ex.getStatusCode());
            ex.printStackTrace();
        }
    }

    public CreateUsersService() {
    }
}
