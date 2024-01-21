package com.equipo5.feelflowapp.dto.modules;

import com.equipo5.feelflowapp.domain.enumerations.modules.SurveyStateEnum;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

@Schema(
        name = "SurveyResponseDto",
        description = "Schema to hold survey information"
)
public record SurveyResponseDto(
        @Schema(description = "List of activities") List<ActivityDto> activities,
        @Schema(description = "State of survey") SurveyStateEnum surveyState
        ){}
