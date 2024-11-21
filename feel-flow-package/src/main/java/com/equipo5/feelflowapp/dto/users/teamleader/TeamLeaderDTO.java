package com.equipo5.feelflowapp.dto.users.teamleader;

import com.equipo5.feelflowapp.constants.validation.admin.ValidationAdminMessages;
import com.equipo5.feelflowapp.constants.validation.teamleader.ValidationTeamLeaderMessages;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;

import java.util.UUID;

@Schema(
        name = "TeamLeaderDto",
        description = "Schema to hold Team Leader Information"
)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TeamLeaderDTO {

    @Schema(description = "Team Leader's Id")
    private UUID uuid;

    @Schema(description = "Team Leader's Name", example = "Jose")
    @NotBlank(message = ValidationTeamLeaderMessages.VALIDATION_MESSAGE_TL_NOT_BLANK_NAME)
    @NotNull(message = ValidationTeamLeaderMessages.VALIDATION_MESSAGE_TL_NOT_NULL_NAME)
    private String name;

    @Schema(description = "Team Leader's Surname", example = "fapencio")
    @NotBlank(message = ValidationTeamLeaderMessages.VALIDATION_MESSAGE_TL_NOT_BLANK_SURNAME)
    @NotNull(message = ValidationTeamLeaderMessages.VALIDATION_MESSAGE_TL_NOT_NULL_SURNAME)
    private String surname;

    @Schema(description = "Team Leader's Username", example = "josefap")
    @NotNull(message = ValidationTeamLeaderMessages.VALIDATION_MESSAGE_TL_NOT_NULL_EMAIL)
    @NotBlank(message = ValidationTeamLeaderMessages.VALIDATION_MESSAGE_TL_NOT_BLANK_EMAIL)
    @Email(message = ValidationTeamLeaderMessages.VALIDATION_MESSAGE_NOT_WELL_FORMED_EMAIL)
    private String username;

    @Schema(description = "Team Leader's Password", example = "RiverCapo1234@")
    @NotNull(message = ValidationTeamLeaderMessages.VALIDATION_MESSAGE_TL_NOT_NULL_PASSWORD)
    @NotBlank(message = ValidationTeamLeaderMessages.VALIDATION_MESSAGE_TL_NOT_BLANK_PASSWORD)
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{8,99}$", message = ValidationAdminMessages.VALIDATION_MESSAGE_ADMIN_NOT_WELL_FORMED_PASSWORD)
    private String password;
}
