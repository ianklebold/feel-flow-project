package com.equipo5.feelflowapp.exception.notfound;

public class NotFoundTeamException extends NotFoundException{
    public NotFoundTeamException() {
    }

    public NotFoundTeamException(String message) {
        super(message);
    }

    public NotFoundTeamException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotFoundTeamException(Throwable cause) {
        super(cause);
    }

    public NotFoundTeamException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
