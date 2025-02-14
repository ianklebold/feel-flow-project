package com.equipo5.feelflowapp.dto.badges;

import com.equipo5.feelflowapp.domain.enumerations.modules.BadgeName;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

@Schema(
        name = "BadgesAwardedDto",
        description = "Schema to hold badge information"
)
public record BadgesAwardedDto(

        @NotNull
        @Schema(description = "Name of badge", example = "MANOS_AMIGAS") BadgeName badgeName,

        @NotNull
        @Schema(description = "id of member of team") UUID idMember
) {
}
