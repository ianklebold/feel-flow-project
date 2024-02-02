package com.equipo5.feelflowapp.dto.modules;

import com.equipo5.feelflowapp.domain.enumerations.modules.SurveyStateEnum;
import com.equipo5.feelflowapp.dto.users.RegularUserDTO;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

@Schema(
        name = "SurveyDto",
        description = "Schema to hold survey information"
)
public record SurveyDto(
        @Schema(description = "Id of the Survey") Long idSurvey,
        @Schema(description = "State of the Survey", examples = {"ACTIVE","FINISHED","CLOSED"})  SurveyStateEnum surveyState,
        RegularUserDTO regularUser,
        List<ActivityDto> activityList,
        ModuleDto module) {
}
