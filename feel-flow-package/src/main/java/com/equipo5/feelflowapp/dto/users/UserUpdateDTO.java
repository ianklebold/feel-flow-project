package com.equipo5.feelflowapp.dto.users;

import com.equipo5.feelflowapp.constants.validation.admin.ValidationAdminMessages;
import com.equipo5.feelflowapp.constants.validation.user.ValidationUserMessages;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserUpdateDTO {

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
}
