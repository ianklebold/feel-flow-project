package com.equipo5.feelflowapp.dto.users;

import com.equipo5.feelflowapp.constants.validation.admin.ValidationAdminMessages;
import com.equipo5.feelflowapp.constants.validation.user.ValidationUserMessages;
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
public class RegularUserDTO {
    private UUID uuid;

    @NotBlank(message = ValidationUserMessages.VALIDATION_MESSAGE_NOT_BLANK_NAME)
    @NotNull(message = ValidationUserMessages.VALIDATION_MESSAGE_NOT_NULL_NAME)
    private String name;

    @NotBlank(message = ValidationUserMessages.VALIDATION_MESSAGE_NOT_BLANK_SURNAME)
    @NotNull(message = ValidationUserMessages.VALIDATION_MESSAGE_NOT_NULL_SURNAME)
    private String surname;

    @NotNull(message = ValidationUserMessages.VALIDATION_MESSAGE_NOT_NULL_EMAIL)
    @NotBlank(message = ValidationUserMessages.VALIDATION_MESSAGE_NOT_BLANK_EMAIL)
    @Email(message = ValidationAdminMessages.VALIDATION_MESSAGE_NOT_WELL_FORMED_EMAIL)
    private String username;

    @NotNull(message = ValidationUserMessages.VALIDATION_MESSAGE_NOT_NULL_PASSWORD)
    @NotBlank(message = ValidationUserMessages.VALIDATION_MESSAGE_NOT_BLANK_PASSWORD)
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{8,99}$", message = ValidationAdminMessages.VALIDATION_MESSAGE_ADMIN_NOT_WELL_FORMED_PASSWORD)
    private String password;

    private TeamDTO teamDTO;
}
