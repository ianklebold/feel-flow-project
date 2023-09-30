package com.equipo5.feelflowapp.dto.users.teamleader;

import com.equipo5.feelflowapp.constants.validation.admin.ValidationAdminMessages;
import com.equipo5.feelflowapp.constants.validation.teamleader.ValidationTeamLeaderMessages;
import com.equipo5.feelflowapp.dto.team.TeamDTO;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;

import java.util.UUID;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TeamLeaderDTO {
    private UUID uuid;

    @NotBlank(message = ValidationTeamLeaderMessages.VALIDATION_MESSAGE_TL_NOT_BLANK_NAME)
    @NotNull(message = ValidationTeamLeaderMessages.VALIDATION_MESSAGE_TL_NOT_NULL_NAME)
    private String name;

    @NotBlank(message = ValidationTeamLeaderMessages.VALIDATION_MESSAGE_TL_NOT_BLANK_SURNAME)
    @NotNull(message = ValidationTeamLeaderMessages.VALIDATION_MESSAGE_TL_NOT_NULL_SURNAME)
    private String surname;

    @NotNull(message = ValidationTeamLeaderMessages.VALIDATION_MESSAGE_TL_NOT_NULL_EMAIL)
    @NotBlank(message = ValidationTeamLeaderMessages.VALIDATION_MESSAGE_TL_NOT_BLANK_EMAIL)
    @Email(message = ValidationTeamLeaderMessages.VALIDATION_MESSAGE_NOT_WELL_FORMED_EMAIL)
    private String username;

    @NotNull(message = ValidationTeamLeaderMessages.VALIDATION_MESSAGE_TL_NOT_NULL_PASSWORD)
    @NotBlank(message = ValidationTeamLeaderMessages.VALIDATION_MESSAGE_TL_NOT_BLANK_PASSWORD)
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{8,99}$", message = ValidationAdminMessages.VALIDATION_MESSAGE_ADMIN_NOT_WELL_FORMED_PASSWORD)
    private String password;
}
