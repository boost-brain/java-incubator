package boost.brain.course.controller;

import boost.brain.course.Constants;
import boost.brain.course.common.auth.Credentials;
import boost.brain.course.email.SendEmail;
import boost.brain.course.entity.EmailEntity;
import boost.brain.course.exceptions.CreateCredentialsException;
import boost.brain.course.model.User;
import boost.brain.course.service.auth.AuthService;
import boost.brain.course.service.register.RegisterService;
import boost.brain.course.service.usersMichaelKotor.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import java.io.IOException;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(Constants.REGISTER_PREFIX)
public class Controller {

    @Autowired
    RegisterService registerService;
    @Autowired
    UserService userService;
    @Autowired
    AuthService authService;


    @GetMapping(value = "/")
    public String registrationForm(){
        return "registrationForm";
    }

    @PostMapping(value = "/")
    public EmailEntity addEmail(@RequestBody EmailEntity entity) throws IOException, MessagingException {
        SendEmail.sendEmail(entity);
        return registerService.addUserEmail(entity);
    }

    @GetMapping (value = "/verification_token/{token}")
    public void verificationToken(@PathVariable("token") String token, EmailEntity emailEntity){
        registerService.confirmation(emailEntity, token);
        registerService.updateConfirmed(emailEntity);
    }

    @PostMapping(path = Constants.CREATE_PREFIX,
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public User createUser(@RequestBody User user){

        /** auth-Service */
        if (!createCredentials(user.getEmail(), user.getPassword())) {
            throw new CreateCredentialsException();
        }
        /** user-Service */
        return userService.createUser(user);
    }

    public boolean createCredentials(String login, String password){

        Credentials credentials = new Credentials();
        credentials.setLogin(login);
        credentials.setPassword(password);

        return authService.createCredentials(credentials);
    }
}
