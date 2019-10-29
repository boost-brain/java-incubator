package boost.brain.course.frontend.administrator.createUser;

import boost.brain.course.common.auth.Credentials;
import boost.brain.course.common.users.UserDto;

import lombok.Data;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.annotation.RequestScope;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;

@Named
@RequestScope
@Data
@Log
public class CreateUsers {

    private String name;
    private String email;
    private String password;
    private String gitHubId;
    private int hours;

    private RestTemplate restTemplate;
    private UserDto userDto = new UserDto();
    private Credentials credentials = new Credentials();;

    FacesContext context = FacesContext.getCurrentInstance();
    FacesMessage message;

    @Value("${users-service-url}")
    private String urlUsers;

    @Value("${auth-credentials-url}")
    private String urlAuth;

    public void addNewUser(String name, String email, String password, String gitHubId, int hours) {
        if (!name.isEmpty() & !email.isEmpty() & !password.isEmpty() & !gitHubId.isEmpty()) {
            userDto.setEmail(email);
            userDto.setGitHubId(gitHubId);
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
                    context.getPartialViewContext().getRenderIds().add("globalMessage");
                }

            } catch (Exception ex) {

                message = new FacesMessage(FacesMessage.SEVERITY_ERROR,
                        "Внимание !!!",
                        "Корректно заполните все поля");
                context.addMessage(null, message);
                context.getPartialViewContext().getRenderIds().add("globalMessage");
                System.out.println(ex.getCause());
            }

        } else {
            message = new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Внимание !!!",
                    "Корректно заполните все поля");
            context.addMessage(null, message);
            context.getPartialViewContext().getRenderIds().add("globalMessage");
        }
    }

    public void addNewCredentials(String email, String password) {
        credentials.setLogin(email);
        credentials.setPassword(password);
        try {
            restTemplate = new RestTemplate();
            restTemplate.postForObject(urlAuth + "/create", credentials, Boolean.class);
            message = new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Пользователь создан !!!",
                    "Данные внесены корректно");
            context.addMessage(null, message);
            context.getPartialViewContext().getRenderIds().add("globalMessage");
        } catch (Exception ex) {
            message = new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Внимание !!!",
                    "Корректно заполните все поля");
            context.addMessage(null, message);
            context.getPartialViewContext().getRenderIds().add("globalMessage");
            System.out.println(ex.getCause());
        }
    }
}
