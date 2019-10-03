package boost.brain.course.frontend.administrator.createUser.service;

import boost.brain.course.common.auth.Credentials;

import boost.brain.course.common.users.UserDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.annotation.RequestScope;

import javax.inject.Named;
import java.io.Serializable;

@Named
@RequestScope
public class CreateUsersService {

    private String name;
    private String email;
    private String password;
    private String gitHabId;
    private int hours;


    @Value("${users-service-url}")
    private String urlUsers;


//    @Value("${auth-login-url}")
//    private String urlAuth;

    //private String urlUsers = "http://users-service:8080/api/users/create";
    private String urlAuth = "http://auth-service:8080/api/credentials/create";

    private RestTemplate restTemplate;
    private UserDto userDto = new UserDto();
    private Credentials credentials;
    String resultMessage;

    public void addNewUser(String name, String email, String password, String gitHabId, int hours) {

        userDto.setEmail(email);
        userDto.setGitHabId(gitHabId);
        userDto.setName(name);
        userDto.setHours(hours);


        try {
            restTemplate = new RestTemplate();
            restTemplate.postForObject(urlUsers+"/create", userDto, UserDto.class);
            addNewCredentials(email, password);
        } catch (HttpStatusCodeException ex) {
            System.out.println(ex.getStatusCode());
            ex.printStackTrace();
        }
    }

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getGitHabId() {
        return gitHabId;
    }

    public void setGitHabId(String gitHabId) {
        this.gitHabId = gitHabId;
    }

    public int getHours() {
        return hours;
    }

    public void setHours(int hours) {
        this.hours = hours;
    }

    public String getUrlUsers() {
        return urlUsers;
    }

    public void setUrlUsers(String urlUsers) {
        this.urlUsers = urlUsers;
    }

    public String getUrlAuth() {
        return urlAuth;
    }

    public void setUrlAuth(String urlAuth) {
        this.urlAuth = urlAuth;
    }

    public String getResultMessage() {
        return resultMessage;
    }

    public void setResultMessage(String resultMessage) {
        this.resultMessage = resultMessage;
    }
}
