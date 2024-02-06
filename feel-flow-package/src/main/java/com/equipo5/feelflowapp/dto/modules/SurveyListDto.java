package com.equipo5.feelflowapp.dto.modules;

import com.equipo5.feelflowapp.domain.enumerations.modules.SurveyStateEnum;
import com.equipo5.feelflowapp.dto.users.RegularUserListDto;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;
import java.util.UUID;

@Schema(
        name = "SurveyListDto",
        description = "Schema to hold survey information"
)
public record SurveyListDto(
        @Schema(description = "Id of the Survey") Long idSurvey,
        @Schema(description = "State of the Survey", examples = {"ACTIVE","FINISHED","CLOSED"}) SurveyStateEnum surveyState,
        List<ActivityDto> activityList,
        RegularUserListDto regularUserListDto
) {
}
