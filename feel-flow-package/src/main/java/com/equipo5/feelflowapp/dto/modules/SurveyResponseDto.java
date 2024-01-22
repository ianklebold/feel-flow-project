package com.equipo5.feelflowapp.dto.modules;

import com.equipo5.feelflowapp.domain.enumerations.modules.SurveyStateEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import java.util.List;

@Schema(
        name = "SurveyResponseDto",
        description = "Schema to hold survey information"
)
public record SurveyResponseDto(
        @Valid
        @Schema(description = "List of activities") List<ActivityDto> activities,

        @NotNull(message = "Debe de indicar un estado para la encuesta")
        @Schema(description = "State of survey", examples = {"ACTIVE","FINISHED","CLOSED"}) SurveyStateEnum surveyState
        ){}
