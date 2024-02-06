package com.equipo5.feelflowapp.exception.notfound;

public class NotFoundModuleException extends NotFoundException{
    public NotFoundModuleException() {
    }

    public NotFoundModuleException(String message) {
        super(message);
    }

    public NotFoundModuleException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotFoundModuleException(Throwable cause) {
        super(cause);
    }

    public NotFoundModuleException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
