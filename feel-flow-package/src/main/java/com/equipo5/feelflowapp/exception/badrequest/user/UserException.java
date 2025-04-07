package com.equipo5.feelflowapp.exception.badrequest.user;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Already a module is active")
public class UserException  extends IllegalArgumentException{
    public UserException() {
    }

    public UserException(String s) {
        super(s);
    }

    public UserException(String message, Throwable cause) {
        super(message, cause);
    }

    public UserException(Throwable cause) {
        super(cause);
    }
}
