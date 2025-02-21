package com.equipo5.feelflowapp.dto.badges;

import com.equipo5.feelflowapp.domain.enumerations.modules.BadgeName;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDate;

@Schema(
        name = "BadgesDto",
        description = "Schema to hold badge information"
)
public record BadgeDto(
        @Schema(description = "Name of badge", example = "MANOS_AMIGAS") BadgeName badgeName,
        @Schema(description = "Date of awarded badge", example = "1970-01-01") LocalDate awardedDate
) {
}
