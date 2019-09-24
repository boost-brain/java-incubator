package boost.brain.course.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class EmailOrNameExistException extends ResponseStatusException {

    public EmailOrNameExistException() {
        super(HttpStatus.INTERNAL_SERVER_ERROR, "Email or name already exist");
    }
}
