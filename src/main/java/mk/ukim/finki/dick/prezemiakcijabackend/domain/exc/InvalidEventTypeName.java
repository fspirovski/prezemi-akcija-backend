package mk.ukim.finki.dick.prezemiakcijabackend.domain.exc;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class InvalidEventTypeName extends RuntimeException {
    public InvalidEventTypeName(String eventTypeName) {
        super(String.format("The provided event type name (%s) is invalid.", eventTypeName));
    }
}
