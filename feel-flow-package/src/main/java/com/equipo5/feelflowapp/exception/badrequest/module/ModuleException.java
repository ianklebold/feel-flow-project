package com.equipo5.feelflowapp.exception.badrequest.module;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Module Exception error")
public class ModuleException extends IllegalArgumentException{
    public ModuleException() {
    }

    public ModuleException(String s) {
        super(s);
    }

    public ModuleException(String message, Throwable cause) {
        super(message, cause);
    }

    public ModuleException(Throwable cause) {
        super(cause);
    }
}
