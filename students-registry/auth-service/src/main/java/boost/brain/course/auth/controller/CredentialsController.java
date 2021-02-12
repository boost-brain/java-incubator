package boost.brain.course.auth.controller;

import boost.brain.course.auth.Constants;
import boost.brain.course.auth.exception.ForbiddenException;
import boost.brain.course.auth.exception.NotFoundException;
import boost.brain.course.auth.repository.CredentialsRepository;
import boost.brain.course.common.auth.Credentials;
import boost.brain.course.common.auth.UserRole;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.HashSet;
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
    @PostMapping(path = Constants.CREATE_PREFIX, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public boolean create(
            @RequestBody Credentials credentials,
            @RequestAttribute("user-roles") Set<UserRole> rolesRequestAttr
    ){
        log.info("CredentialsController: create method started with credentials.login = " + credentials.getLogin());

        //Checking access rights
        HashSet<UserRole> rolesForAccess = new HashSet<>(Arrays.asList(UserRole.ADMINISTRATOR));
        if(!this.accessOnlyForRoles(rolesRequestAttr, rolesForAccess)) {
            log.severe("CredentialsController: create method -> Access denied, required role missing!" );
            throw new ForbiddenException();
        }

        //Creating credentials
        boolean credentialsCreated = credentialsRepository.create(credentials);
        log.info("CredentialsController: create method completed with result = " + credentialsCreated);
        return credentialsCreated;
    }

    @Override
    @PatchMapping(
            path = Constants.UPDATE_PREFIX,
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE
    )
    public boolean update(
            @RequestBody Credentials credentials,
            @RequestAttribute("user-roles") Set<UserRole> rolesRequestAttr
    ) {
        log.info("CredentialsController: update method started with credentials = " + credentials.toString());

        //Checking access rights
        HashSet<UserRole> rolesForAccess = new HashSet<>(Arrays.asList(UserRole.ADMINISTRATOR));
        if(!this.accessOnlyForRoles(rolesRequestAttr, rolesForAccess)) {
            log.severe("CredentialsController: update method -> Access denied, required role missing!" );
            throw new ForbiddenException();
        }
        //Updating credentials
        boolean credentialsUpdated = credentialsRepository.update(credentials);
        log.info("CredentialsController: update method completed with result = " + credentialsUpdated);
        return credentialsUpdated;
    }

    @Override
    @DeleteMapping(path = Constants.DELETE_PREFIX + "/{login}")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable String login, @RequestAttribute("user-roles") Set<UserRole> rolesRequestAttr
    ){
        log.info("CredentialsController: delete method started with login = " + login);
        //Checking access rights
        HashSet<UserRole> rolesForAccess = new HashSet<>(Arrays.asList(UserRole.ADMINISTRATOR));
        if(!this.accessOnlyForRoles(rolesRequestAttr, rolesForAccess)) {
            log.severe("CredentialsController: delete method -> Access denied, required role missing!" );
            throw new ForbiddenException();
        }
        //Delete credentials
        if(!credentialsRepository.delete(login)){
            throw new NotFoundException();
        }
        log.info("CredentialsController: delete method completed.");
    }

    @Override
    @GetMapping(path = Constants.USER_ROLE_PREFIX + Constants.READ_PREFIX + "/{login}")
    public Set<UserRole> readUserRoles(@PathVariable String login) {
        return credentialsRepository.readUserRoles(login);
    }

    @Override
    @PatchMapping(
            path = Constants.USER_ROLE_PREFIX + Constants.UPDATE_PREFIX + "/{login}",
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE
    )
    public boolean updateUserRoles(
            @RequestBody Set<UserRole> updateRoles,
            @PathVariable String login,
            @RequestAttribute("user-roles") Set<UserRole> rolesRequestAttr
    ) {
        log.info("CredentialsController: updateUserRoles method started with login = " + login);

        //Checking access rights
        HashSet<UserRole> rolesForAccess = new HashSet<>(Arrays.asList(UserRole.ADMINISTRATOR));
        if(!this.accessOnlyForRoles(rolesRequestAttr, rolesForAccess)) {
            log.severe("CredentialsController: updateUserRoles method -> Access denied, required role missing!" );
            throw new ForbiddenException();
        }

        //Updating user roles
        boolean userRolesUpdated = credentialsRepository.updateUserRoles(updateRoles, login);
        log.info("CredentialsController: updateUserRoles method completed with result = " + userRolesUpdated);
        return userRolesUpdated;
    }

    private boolean accessOnlyForRoles(Set<UserRole> roles, Set<UserRole> rolesForAccess) {
        if (rolesForAccess == null || roles == null) {
            return false;
        }
        return rolesForAccess.stream().anyMatch(roles::contains);
    }
}
