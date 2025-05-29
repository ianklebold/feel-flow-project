package com.equipo5.feelflowapp.dto.dashboard.kudos;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Schema(
        name = "KudosSummaryData",
        description = "Schema to hold KudosSummaryData"
)
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class KudosSummaryData{
    private String name;
    private String username;
    private int cantBadges;
    private boolean highlight;
}
