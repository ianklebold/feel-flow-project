package com.equipo5.feelflowapp.dto.badges;

import com.equipo5.feelflowapp.domain.enumerations.modules.BadgeName;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

@Schema(
        name = "BadgesAvailableDto",
        description = "Schema to hold badge available information"
)
public record BadgesAvailableDto(
        @Schema(description = "Name of badge", example = "MANOS_AMIGAS") BadgeName badgeName,

        @Schema(description = "Number of kind of badge available") int numberOfBadges
) {}
