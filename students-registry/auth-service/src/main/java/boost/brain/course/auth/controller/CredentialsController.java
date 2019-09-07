package boost.brain.course.auth.controller;

import boost.brain.course.auth.Constants;
import boost.brain.course.auth.exception.NotFoundException;
import boost.brain.course.auth.repository.CredentialsRepository;
import boost.brain.course.common.auth.Credentials;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(path = Constants.CREDENTIALS_CONTROLLER_PREFIX)
public class CredentialsController {
    private final CredentialsRepository credentialsRepository;

    @Autowired
    public CredentialsController(CredentialsRepository credentialsRepository) {
        this.credentialsRepository = credentialsRepository;
    }

    @PostMapping(path = Constants.CREATE_PREFIX,
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Credentials create(@RequestBody Credentials credentials){
        Credentials result =  credentialsRepository.create(credentials);
        return result;
    }

    @PatchMapping(path = Constants.UPDATE_PREFIX,
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public boolean update(@RequestBody Credentials credentials){
        return credentialsRepository.update(credentials);
    }

    @DeleteMapping(path = Constants.DELETE_PREFIX + "/{login}")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable String login){
        if(!credentialsRepository.delete(login)){
            throw new NotFoundException();
        }

    }
}
