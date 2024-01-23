package com.equipo5.feelflowapp.exception.badrequest.survey;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class SurveyException  extends IllegalArgumentException{
    public SurveyException() {
    }

    public SurveyException(String s) {
        super(s);
    }

    public SurveyException(String message, Throwable cause) {
        super(message, cause);
    }

    public SurveyException(Throwable cause) {
        super(cause);
    }
}
