package com.equipo5.feelflowapp.exception.module;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Already exist one module active")
public class TwelveStepsModuleException extends Exception{
    public TwelveStepsModuleException() {
    }

    public TwelveStepsModuleException(String message) {
        super(message);
    }

    public TwelveStepsModuleException(String message, Throwable cause) {
        super(message, cause);
    }

    public TwelveStepsModuleException(Throwable cause) {
        super(cause);
    }

    public TwelveStepsModuleException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
