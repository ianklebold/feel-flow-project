package com.equipo5.feelflowapp.dto.modules;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(
        name = "SurveyNikoNikoResponseDto",
        description = "Schema to hold survey information"
)
public record SurveyAvailableNikoNikoReponseDto(
        @Schema(description = "Id of the Survey")
        Long idSurvey,
        @Schema(description = "Number of activity")
        int numberOfActivity,
        @Schema(description = "Activity available")
        ActivityDto activityAvailable
) {
}
