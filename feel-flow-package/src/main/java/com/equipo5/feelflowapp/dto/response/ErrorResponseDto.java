package com.equipo5.feelflowapp.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@Schema(
        name = "ErrorResponse",
        description = "Schema to hold error response information"
)
public class ErrorResponseDto {
    @Schema(
            description = "API patch invoked by client"
    )
    private String apiPath;

    @Schema(
            description = "Error code representing the error happened"
    )
    private HttpStatus errorCode;

    @Schema(
            description = "Error code representing the error happened"
    )
    private String errorMessage;

    @Schema(
            description = "Time representing when the error happened"
    )
    private LocalDateTime errorTime;

    public ErrorResponseDto(Exception e, String description,HttpStatus httpStatus) {

        this.setApiPath(description);
        this.setErrorCode(httpStatus);
        this.setErrorMessage(e.getMessage());
        this.setErrorTime(LocalDateTime.now());

    }
}
