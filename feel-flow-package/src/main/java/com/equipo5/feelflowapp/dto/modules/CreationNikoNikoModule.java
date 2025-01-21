package com.equipo5.feelflowapp.dto.modules;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;

import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.UUID;


@Schema(
        name = "CreationModuleNikoNikoDto",
        description = "Schema to create module"
)
public record CreationNikoNikoModule (
    @Schema(description = "team") UUID idTeam,

    @FutureOrPresent(message = "Must to be a date-time of the present or future")
    @NotNull(message = "Must not to be null")
    @Schema(description = "Date and time of the survey for to be available", example = "999999999-12-31T23:59:59" )
    Timestamp dateAndTimeToPublish,

    @Future(message = "Must to be a date-time of the future")
    @NotNull(message = "Must not to be null")
    @Schema(description = "Date and time of the survey for to be closed", example = "999999999-12-31T23:59:59" ) Timestamp dateAndTimeToClose,

    @NotNull(message = "Must not to be null")
    @Schema(description = "Survey time at the beginning of the day", example = "00:00" )
    Time timeToToResponseStartDay,

    @NotNull(message = "Must not to be null")
    @Schema(description = "Survey time at the ending of the day", example = "23:59:59.999999999" )
    Time timeToToResponseEndDay

){}
