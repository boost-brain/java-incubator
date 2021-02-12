package boost.brain.course.auth.controller;


import boost.brain.course.auth.Constants;
import boost.brain.course.auth.repository.SessionsRepository;
import boost.brain.course.common.auth.Credentials;
import boost.brain.course.common.auth.Session;
import boost.brain.course.common.auth.SessionCheck;
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
        log.info("LoginController: login method started with credentials.getLogin =" + credentials.getLogin());
        //Creating new active session for user
        Session startedSession = sessionsRepository.startSession(credentials);
        log.info("LoginController: login method completed.");
        return startedSession;
    }

    @Override
    @PostMapping(path = Constants.LOGOUT_PREFIX)
    public boolean logout(@RequestHeader("sessionId") String sessionId) {
        log.info("LoginController: logout method started with sessionId = " + sessionId);
        //Closing session
        boolean closedSession = sessionsRepository.closeSession(sessionId);
        log.info("LoginController: logout method completed.");
        return closedSession;
    }

    @Override
    @PostMapping(path = Constants.CHECK_PREFIX)
    public SessionCheck getCheckSession(@RequestHeader("sessionId") String sessionId) {
        log.info("LoginController: getCheckSession method started with sessionId = " + sessionId);
        //Checking and getting session information
        SessionCheck sessionCheck = sessionsRepository.getCheckSession(sessionId);
        log.info("LoginController: getCheckSession method completed.");
        return sessionCheck;
    }
}
