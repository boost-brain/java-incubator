package boost.brain.course.frontend.administrator.createUser;

import boost.brain.course.common.auth.Credentials;
import boost.brain.course.common.users.UserDto;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.annotation.RequestScope;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;

@Named
@RequestScope
@Data
@Slf4j
public class CreateUsers {

    private String name;
    private String email;
    private String password;
    private String gitHubId;
    private Integer hours;

    private RestTemplate restTemplate = new RestTemplate();
    private UserDto userDto = new UserDto();
    private Credentials credentials = new Credentials();


    FacesContext context = FacesContext.getCurrentInstance();
    FacesMessage message;

    @Value("${users-service-url}")
    private String urlUsers;

    @Value("${auth-credentials-url}")
    private String urlAuth;

    public void addNewUser(String name, String email, String password, String gitHubId, Integer hours) {
        if (!name.isEmpty() & !email.isEmpty() & !password.isEmpty() & !gitHubId.isEmpty()) {
            if(hours==null) {
                hours=1;
            }
            userDto.setEmail(email);
            userDto.setGitHubId(gitHubId);
            userDto.setName(name);
            userDto.setHours(hours);

            UserDto userDtoResp = restTemplate.postForObject(urlUsers + "/create", this.userDto, UserDto.class);
            if (userDtoResp != null) {
                addNewCredentials(email, password);
                log.info("Create user in table user_entity was success");
            } else {
                errorMessage(false);
                log.error("Create user in table user_entity was failed");
            }

        } else {
            errorMessage(false);
            log.error("one of the fields in JSF GUI is empty");
        }
    }

    public void addNewCredentials(String email, String password) {
        credentials.setLogin(email);
        credentials.setPassword(password);
        try {
            restTemplate.postForObject(urlAuth + "/create", credentials, Boolean.class);
            errorMessage(true);
            log.info("Create credentials in table credentials_entity was success");
        } catch (Exception ex) {
            errorMessage(false);
            log.error("Create credentials in table credentials_entity was failed" + ex.getCause());
        }
    }

    private void errorMessage(boolean numberOfError) {
        if (numberOfError) {
            message = new FacesMessage(FacesMessage.SEVERITY_INFO,
                    "Пользователь создан !!!",
                    "Данные внесены корректно");
        } else {
            message = new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Внимание !!!",
                    "Корректно заполните все поля");
        }
        context.addMessage(null, message);
        context.getPartialViewContext().getRenderIds().add("globalMessage");
    }
}