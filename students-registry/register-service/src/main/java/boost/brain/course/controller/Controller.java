package boost.brain.course.controller;

import boost.brain.course.email.SendEmail;
import boost.brain.course.entity.EmailEntity;
import boost.brain.course.model.Account;
import boost.brain.course.service.accountArkasandr.AccountService;
import boost.brain.course.service.register.RegisterService;
import boost.brain.course.model.User;
import boost.brain.course.service.usersMichaelKotor.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import java.io.IOException;

@RestController
public class Controller {

    @Autowired
    RegisterService registerService;
    @Autowired
    UserService userService;
    @Autowired
    AccountService accountService;

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

    @PostMapping(value = "/createUser")
    public User createUser(@RequestParam String name,
                           @RequestParam String email,
                           @RequestParam int hours){

        User user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setHours(hours);

        return userService.createUser(user);
    }

    @PostMapping(value = "/createAccount")
    public Account createAccount(@RequestParam String email,
                                 @RequestParam String password){

        Account account = new Account();
        account.setEmail(email);
        account.setPassword(password);

        return accountService.createAccount(account);
    }
}
