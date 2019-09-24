package boost.brain.course.controller;

import boost.brain.course.Constants;
import boost.brain.course.common.auth.Credentials;
import boost.brain.course.common.auth.UserDto;
import boost.brain.course.entity.EmailEntity;
import boost.brain.course.exceptions.AddEmailException;
import boost.brain.course.exceptions.CreateCredentialsException;
import boost.brain.course.model.User;
import boost.brain.course.service.auth.AuthService;
import boost.brain.course.service.register.RegisterService;
import boost.brain.course.service.register.RegisterServiceImpl;
import boost.brain.course.service.users.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(Constants.REGISTER_PREFIX)
public class Controller {

    @Value("${url.host}")
    private String host;

    @Value("${url.port.jsf-frontend}")
    private String port;

    @Autowired
    RegisterService registerService;
    @Autowired
    UserService userService;
    @Autowired
    AuthService authService;


    @PostMapping(path = Constants.CREATE_PREFIX,
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public UserDto createUser(@RequestBody User user){

        /** addEmail in register database */
        EmailEntity emailEntity = new EmailEntity();
        emailEntity.setEmail(user.getEmail());
        emailEntity.setName(user.getName());

        try {
            if ( addEmail(emailEntity) == null) {
                throw new AddEmailException();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (MessagingException e) {
            e.printStackTrace();
        }

        /** auth-Service */
        if (!createCredentials(user.getEmail(), user.getPassword())) {
            throw new CreateCredentialsException();
        }

        /** user-Service */
        UserDto commonUser = new UserDto();
        BeanUtils.copyProperties(user, commonUser);
        return userService.createUser(commonUser);
    }

    @GetMapping (value = "/verification_token/{token}")
    public void verificationToken(@PathVariable("token") String token, EmailEntity emailEntity,
                                  HttpServletResponse response){
        registerService.confirmation(emailEntity, token);
        registerService.updateConfirmed(emailEntity);

        String redirectUrl = host + ":" + port + "/email_sending-confirmed.xhtml";
        response.setHeader("Location", redirectUrl);
        response.setStatus(302);
    }

    public EmailEntity addEmail(EmailEntity entity) throws IOException, MessagingException {
        return registerService.addUserEmail(entity);
    }

    public boolean createCredentials(String login, String password){

        Credentials credentials = new Credentials();
        credentials.setLogin(login);
        credentials.setPassword(password);

        return authService.createCredentials(credentials);
    }
}
