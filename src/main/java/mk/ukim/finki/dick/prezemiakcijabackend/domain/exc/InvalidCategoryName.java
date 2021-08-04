package mk.ukim.finki.dick.prezemiakcijabackend.domain.exc;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class InvalidCategoryName extends RuntimeException {
    public InvalidCategoryName(String categoryName) {
        super(String.format("The provided category name (%s) is invalid.", categoryName));
    }
}
