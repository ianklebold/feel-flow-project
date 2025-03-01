package com.equipo5.feelflowapp.exception.badrequest.badge;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Badge is not possible to assign")
public class BadgeIsNotPossibleAssignException extends IllegalArgumentException{
    public BadgeIsNotPossibleAssignException() {
    }

    public BadgeIsNotPossibleAssignException(String s) {
        super(s);
    }

    public BadgeIsNotPossibleAssignException(String message, Throwable cause) {
        super(message, cause);
    }

    public BadgeIsNotPossibleAssignException(Throwable cause) {
        super(cause);
    }
}
