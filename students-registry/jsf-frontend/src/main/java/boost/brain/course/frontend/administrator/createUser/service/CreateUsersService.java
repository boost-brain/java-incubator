package boost.brain.course.frontend.administrator.createUser.service;

import boost.brain.course.common.auth.Credentials;
import boost.brain.course.common.users.UserDto;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.annotation.RequestScope;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;

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

    FacesContext context = FacesContext.getCurrentInstance();
    FacesMessage message;


//    @Value("${auth-login-url}")
//    private String urlAuth;

    private String urlAuth = "http://auth-service:8080/api/credentials/create";

    private RestTemplate restTemplate;
    private UserDto userDto = new UserDto();
    private Credentials credentials;

    public void addNewUser(String name, String email, String password, String gitHabId, int hours) {
        if (name != null & email != null & password != null & gitHabId != null) {
            userDto.setEmail(email);
            userDto.setGitHabId(gitHabId);
            userDto.setName(name);
            userDto.setHours(hours);

            try {
                restTemplate = new RestTemplate();
                UserDto userDtoResp = restTemplate.postForObject(urlUsers + "/create", this.userDto, UserDto.class);
                if (userDtoResp != null) {
                    addNewCredentials(email, password);
                } else {
                    message = new FacesMessage(FacesMessage.SEVERITY_ERROR,
                            "Внимание !!!",
                            "Корректно заполните все поля");
                    context.addMessage(null, message);
                    context.validationFailed();
                }

            } catch (Exception ex) {

                message = new FacesMessage(FacesMessage.SEVERITY_ERROR,
                        "Внимание !!!",
                        "Корректно заполните все поля");
                context.addMessage(null, message);
                context.validationFailed();
                System.out.println(ex.getCause());
            }

        } else {
            message = new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Внимание !!!",
                    "Корректно заполните все поля");
            context.addMessage(null, message);
            context.validationFailed();
        }
    }

    public void addNewCredentials(String email, String password) {
        credentials = new Credentials(email, password);
        try {
            restTemplate = new RestTemplate();
            restTemplate.postForObject(urlAuth, credentials, Boolean.class);
            message = new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Пользователь создан !!!",
                    "Данные внесены корректно");
            context.addMessage(null, message);
            context.validationFailed();
        } catch (Exception ex) {
            message = new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Внимание !!!",
                    "Корректно заполните все поля");
            context.addMessage(null, message);
            context.validationFailed();
            System.out.println(ex.getCause());
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
}
