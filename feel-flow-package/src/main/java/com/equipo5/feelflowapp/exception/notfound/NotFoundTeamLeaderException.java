package com.equipo5.feelflowapp.exception.notfound;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Not Exist Team Leader For Team")
public class NotFoundTeamLeaderException extends NotFoundException {
    public NotFoundTeamLeaderException() {
    }

    public NotFoundTeamLeaderException(String message) {
        super(message);
    }

    public NotFoundTeamLeaderException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotFoundTeamLeaderException(Throwable cause) {
        super(cause);
    }

    public NotFoundTeamLeaderException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
