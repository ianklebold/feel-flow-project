package com.equipo5.feelflowapp.dto.badges;

import com.equipo5.feelflowapp.domain.enumerations.modules.BadgeName;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDate;

@Schema(
        name = "BadgesTeamDto",
        description = "Schema to hold team badge information"
)
public record BadgeTeamDto(
        @Schema(description = "Name of badge", example = "MANOS_AMIGAS") BadgeName badgeName,
        @Schema(description = "Number of badges", example = "50") long numberOfBadges
) {
}
