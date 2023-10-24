package com.equipo5.feelflowapp.exception.module;

import com.equipo5.feelflowapp.exception.notfound.NotFoundException;

public class ModuleNotFoundException  extends NotFoundException {
    public ModuleNotFoundException() {
    }

    public ModuleNotFoundException(String message) {
        super(message);
    }

    public ModuleNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public ModuleNotFoundException(Throwable cause) {
        super(cause);
    }

    public ModuleNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
