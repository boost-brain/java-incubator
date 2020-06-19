package boost.brain.course.auth.controller;

/**
 * REST контроллер сервиса управления Сессиями (Session).
 * в интерфейсе LoginControllerApiMethodSwaggerAnnotation содержится информация для сервиса документации Swagger
 */
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
public class LoginController implements LoginControllerApiMethodSwaggerAnnotation{
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
        return sessionsRepository.startSession(credentials);
    }

    @Override
    @GetMapping(path = Constants.LOGOUT_PREFIX + "/{sessionId}")
    public boolean logout(@PathVariable String sessionId){
        Session session = new Session();
        session.setSessionId(sessionId);

        return sessionsRepository.closeSession(session);
    }

    @Override
    @GetMapping(path = Constants.CHECK_PREFIX + "/{sessionId}")
    public boolean checkSession(@PathVariable String sessionId){
        Session session = new Session();
        session.setSessionId(sessionId);

        return sessionsRepository.checkSession(session);
    }
}
