package com.equipo5.feelflowapp.dto.badges;

import com.equipo5.feelflowapp.domain.enumerations.modules.BadgeName;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

@Schema(
        name = "BadgesAwardedWithNumberOfBadgesDto",
        description = "Schema to hold badge information"
)
public record BadgeWithNumberOfBadgesDto(
        @NotNull
        @Schema(description = "Name of badge", example = "MANOS_AMIGAS")
        BadgeName badgeName,

        @Schema(description = "Number of badges", example = "1")
        int numberOfBadge
) {}
