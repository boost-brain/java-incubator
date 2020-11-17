package boost.brain.course.auth.controller;

import boost.brain.course.auth.Constants;
import boost.brain.course.auth.exception.NotFoundException;
import boost.brain.course.auth.repository.CredentialsRepository;
import boost.brain.course.common.auth.Credentials;
import boost.brain.course.common.users.UserRole;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@Log
@RequestMapping(path = Constants.CREDENTIALS_CONTROLLER_PREFIX)
public class CredentialsController implements CredentialsControllerSwaggerAnnotations {
    private final CredentialsRepository credentialsRepository;

    @Autowired
    public CredentialsController(CredentialsRepository credentialsRepository) {
        this.credentialsRepository = credentialsRepository;
    }

    @Override
    @PostMapping(path = Constants.CREATE_PREFIX,
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public boolean create(@RequestBody Credentials credentials){
        log.info("CredentialsController: create method started");
        log.info("credentials = " + credentials.toString());
        long startTime = System.nanoTime();
        Boolean credentialsCreated = credentialsRepository.create(credentials);
        log.info("lag of credentialsRepository.create(credentials) = " + (System.nanoTime() - startTime) / 1000000);
        return credentialsCreated;
    }

    @Override
    @PatchMapping(path = Constants.UPDATE_PREFIX,
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public boolean update(@RequestBody Credentials credentials){
        log.info("CredentialsController: update method started");
        log.info("credentials = " + credentials.toString());
        long startTime = System.nanoTime();
        boolean credentialsUpdated = credentialsRepository.update(credentials);
        log.info("lag of credentialsRepository.update(credentials) = " + (System.nanoTime() - startTime) / 1000000);
        return credentialsUpdated;
    }

    @Override
    @DeleteMapping(path = Constants.DELETE_PREFIX + "/{login}")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable String login){
        log.info("CredentialsController: delete method started");
        log.info("login = " + login);
        long startTime = System.nanoTime();
        if(!credentialsRepository.delete(login)){
            throw new NotFoundException();
        }
        log.info("lag of credentialsRepository.delete(login) = " + (System.nanoTime() - startTime) / 1000000);
    }

    @Override
    @GetMapping(path = Constants.USER_ROLE_PREFIX + Constants.READ_PREFIX + "/{login}")
    public Set<UserRole> readUserRoles(@PathVariable String login) {
        return credentialsRepository.readUserRoles(login);
    }

    @Override
    @PatchMapping(path = Constants.USER_ROLE_PREFIX + Constants.UPDATE_PREFIX + "/{login}",
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public boolean updateUserRoles(@RequestBody Set<UserRole> roles, @PathVariable String login) {
        return credentialsRepository.updateUserRoles(roles, login);
    }
}
