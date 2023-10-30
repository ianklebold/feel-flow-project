package com.equipo5.feelflowapp.dto.team;

import com.equipo5.feelflowapp.constants.validation.team.ValidationTeamMessages;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TeamUpdateDTO {

    @NotBlank(message = ValidationTeamMessages.VALIDATION_MESSAGE_TEAM_NOT_BLANK_NAME)
    @NotNull(message = ValidationTeamMessages.VALIDATION_MESSAGE_TEAM_NOT_NULL_NAME)
    private String nameTeam;

    @NotBlank(message = ValidationTeamMessages.VALIDATION_MESSAGE_TEAM_NOT_BLANK_DESCRIPTION)
    @NotNull(message = ValidationTeamMessages.VALIDATION_MESSAGE_TEAM_NOT_NULL_DESCRIPTION)
    private String descriptionTeam;
}
