package com.equipo5.feelflowapp.dto.users.admin;

import com.equipo5.feelflowapp.constants.validation.admin.ValidationAdminMessages;
import com.equipo5.feelflowapp.dto.enterprise.EnterpriseDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.*;

import java.util.UUID;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AdminDTO {
    private UUID uuid;

    @NotBlank(message = ValidationAdminMessages.VALIDATION_MESSAGE_ADMIN_NOT_BLANK_NAME)
    @NotNull(message = ValidationAdminMessages.VALIDATION_MESSAGE_ADMIN_NOT_NULL_NAME)
    private String name;

    @NotNull(message = ValidationAdminMessages.VALIDATION_MESSAGE_ADMIN_NOT_NULL_EMAIL)
    @NotBlank(message = ValidationAdminMessages.VALIDATION_MESSAGE_ADMIN_NOT_BLANK_EMAIL)
    @Email(message = ValidationAdminMessages.VALIDATION_MESSAGE_ADMIN_NOT_WELL_FORMED_EMAIL)
    private String email;

    @NotNull(message = ValidationAdminMessages.VALIDATION_MESSAGE_ADMIN_NOT_NULL_PASSWORD)
    @NotBlank(message = ValidationAdminMessages.VALIDATION_MESSAGE_ADMIN_NOT_BLANK_PASSWORD)
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{8,99}$", message = ValidationAdminMessages.VALIDATION_MESSAGE_ADMIN_NOT_WELL_FORMED_PASSWORD)
    private String password;

    @Valid
    private EnterpriseDTO enterpriseDTO;
}
