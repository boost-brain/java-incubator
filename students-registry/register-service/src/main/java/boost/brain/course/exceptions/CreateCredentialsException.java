package boost.brain.course.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class CreateCredentialsException extends ResponseStatusException {

    public CreateCredentialsException() {
        super(HttpStatus.INTERNAL_SERVER_ERROR, "Error while credentials creation");
    }
}
