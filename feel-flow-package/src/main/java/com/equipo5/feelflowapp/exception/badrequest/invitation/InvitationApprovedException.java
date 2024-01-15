package com.equipo5.feelflowapp.exception.badrequest.invitation;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Invitation was approved")
public class InvitationApprovedException extends InvitationException{
    public InvitationApprovedException() {
    }

    public InvitationApprovedException(String message) {
        super(message);
    }

    public InvitationApprovedException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvitationApprovedException(Throwable cause) {
        super(cause);
    }

    public InvitationApprovedException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
