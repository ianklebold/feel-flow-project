package com.equipo5.feelflowapp.exception.expiration;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Invitation Expired")
public class InvitationExpiredException extends InvitationException{
    public InvitationExpiredException() {
    }

    public InvitationExpiredException(String message) {
        super(message);
    }

    public InvitationExpiredException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvitationExpiredException(Throwable cause) {
        super(cause);
    }

    public InvitationExpiredException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
