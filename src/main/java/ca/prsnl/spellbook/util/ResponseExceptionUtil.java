package ca.prsnl.spellbook.util;

import ca.prsnl.spellbook.service.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class ResponseExceptionUtil {

    public static ResponseStatusException getResponseStatusException(Exception e) {
        if (e instanceof IllegalArgumentException) {
            return new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        } else if (e instanceof ResourceNotFoundException) {
            return new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } else {
            return new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }

    }
}
