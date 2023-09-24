package com.equipo5.feelflowapp.dto.team;

import com.equipo5.feelflowapp.constants.validation.team.ValidationTeamMessages;
import com.equipo5.feelflowapp.dto.users.teamleader.TeamLeaderDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TeamDTO {

    private UUID uuid;

    @NotBlank(message = ValidationTeamMessages.VALIDATION_MESSAGE_TEAM_NOT_BLANK_NAME)
    @NotNull(message = ValidationTeamMessages.VALIDATION_MESSAGE_TEAM_NOT_NULL_NAME)
    private String nameTeam;

    @NotBlank(message = ValidationTeamMessages.VALIDATION_MESSAGE_TEAM_NOT_BLANK_DESCRIPTION)
    @NotNull(message = ValidationTeamMessages.VALIDATION_MESSAGE_TEAM_NOT_NULL_DESCRIPTION)
    private String descriptionTeam;

    @Valid
    private TeamLeaderDTO teamLeaderDTO;
}
