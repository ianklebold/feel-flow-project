package com.equipo5.feelflowapp.dto.modules;

import io.swagger.v3.oas.annotations.media.Schema;

public record SurveyNikoNikoResponseDto(
        @Schema(description = "Id of the Survey")
        Long idSurvey,
        @Schema(description = "Activity available")
        ActivityDto activitySolved
){}
