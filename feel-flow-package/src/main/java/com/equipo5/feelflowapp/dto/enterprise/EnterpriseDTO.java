package com.equipo5.feelflowapp.dto.enterprise;

import com.equipo5.feelflowapp.constants.validation.enterPrise.ValidationEnterpriseMessages;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EnterpriseDTO {

    @NotBlank(message = ValidationEnterpriseMessages.VALIDATION_MESSAGE_ENTERPRISE_NOT_BLANK_NAME)
    @NotNull(message = ValidationEnterpriseMessages.VALIDATION_MESSAGE_ENTERPRISE_NOT_NULL_NAME)
    private String name;
}
