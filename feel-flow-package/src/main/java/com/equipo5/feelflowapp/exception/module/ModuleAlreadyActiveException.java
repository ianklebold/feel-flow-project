package com.equipo5.feelflowapp.exception.module;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Already a module is active")
public class ModuleAlreadyActiveException extends ModuleException{

    public ModuleAlreadyActiveException() {
    }

    public ModuleAlreadyActiveException(String s) {
        super(s);
    }

    public ModuleAlreadyActiveException(String message, Throwable cause) {
        super(message, cause);
    }

    public ModuleAlreadyActiveException(Throwable cause) {
        super(cause);
    }
}
