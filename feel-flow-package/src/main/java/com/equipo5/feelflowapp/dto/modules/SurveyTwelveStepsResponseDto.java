package com.equipo5.feelflowapp.dto.modules;

import com.equipo5.feelflowapp.domain.enumerations.modules.SurveyStateEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;

@Schema(
        name = "SurveyResponseDto",
        description = "Schema to hold survey information"
)
public record SurveyTwelveStepsResponseDto(
        @Valid
        @Size(min = 12, max = 12, message = "The list must have 12 answers")
        @Schema(description = "List of activities") List<ActivityDto> activities,

        @NotNull(message = "Must to complete the state of Survey")
        @Schema(description = "State of survey", examples = {"ACTIVE","FINISHED","CLOSED"}) SurveyStateEnum surveyState
        ){}
