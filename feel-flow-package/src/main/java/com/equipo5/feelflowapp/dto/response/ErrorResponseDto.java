package com.equipo5.feelflowapp.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class ErrorResponseDto {
    private String apiPath;
    private HttpStatus errorCode;
    private String errorMessage;
    private LocalDateTime errorTime;

    public ErrorResponseDto(Exception e, String description,HttpStatus httpStatus) {

        this.setApiPath(description);
        this.setErrorCode(httpStatus);
        this.setErrorMessage(e.getMessage());
        this.setErrorTime(LocalDateTime.now());

    }
}
