package com.equipo5.feelflowapp.dto.tablebadge;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Schema(
        name = "BadgeTableAwardedDto",
        description = "Schema to hold badges awarded information"
)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class CountBadgeAwardedDto {
    private String badgeName;
    private int countAwarded;

    public void incrementCountAwarded() {
        this.countAwarded++;
    }

}
