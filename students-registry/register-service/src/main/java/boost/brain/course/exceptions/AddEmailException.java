package boost.brain.course.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class AddEmailException  extends ResponseStatusException {

    public AddEmailException() {
        super(HttpStatus.INTERNAL_SERVER_ERROR, "Error while adding EmailEntity");
    }
}
