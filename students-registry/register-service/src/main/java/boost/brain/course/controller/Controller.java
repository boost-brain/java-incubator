package boost.brain.course.controller;

import boost.brain.course.email.SendEmail;
import boost.brain.course.entity.EmailEntity;
import boost.brain.course.service.RegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import java.io.IOException;

@RestController
public class Controller {

    @Autowired
    RegisterService registerService;

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
}
