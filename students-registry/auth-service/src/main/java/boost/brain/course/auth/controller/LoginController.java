package boost.brain.course.auth.controller;


import boost.brain.course.auth.Constants;
import boost.brain.course.auth.repository.SessionsRepository;
import boost.brain.course.common.auth.Credentials;
import boost.brain.course.common.auth.Session;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@Log
@RequestMapping(path = Constants.LOGIN_CONTROLLER_PREFIX)
public class LoginController implements LoginControllerSwaggerAnnotations {
    private final SessionsRepository sessionsRepository;

    @Autowired
    public LoginController(SessionsRepository sessionsRepository) {
        this.sessionsRepository = sessionsRepository;
    }

    @Override
    @PostMapping(path = Constants.LOGIN_PREFIX,
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Session login(@RequestBody Credentials credentials){
        log.info("LoginController: login method started");
        log.info("credentials=" + credentials.toString());
        long startTime = System.currentTimeMillis();
        Session startedSession = sessionsRepository.startSession(credentials);
        log.info("lag of sessionsRepository.startSession(credentials) = " + (System.currentTimeMillis() - startTime));
        return startedSession;
    }

    @Override
    @GetMapping(path = Constants.LOGOUT_PREFIX + "/{sessionId}")
    public boolean logout(@PathVariable String sessionId){
        log.info("LoginController: logout method started");
        long startTime = System.currentTimeMillis();
        boolean closedSession = sessionsRepository.closeSession(sessionId);
        log.info("lag of sessionsRepository.closeSession(sessionId) = " + (System.currentTimeMillis() - startTime));

        return closedSession;
    }

    @Override
    @GetMapping(path = Constants.CHECK_PREFIX + "/{sessionId}")
    public boolean checkSession(@PathVariable String sessionId){
        log.info("LoginController: checkSession method started");
        long startTime = System.currentTimeMillis();
        boolean checkedSession = sessionsRepository.checkSession(sessionId);
        log.info("lag of sessionsRepository.checkSession(sessionId) = " + (System.currentTimeMillis() - startTime));

        return checkedSession;
    }
}
