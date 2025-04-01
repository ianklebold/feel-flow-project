package com.equipo5.feelflowapp.dto.dashboard.kudos;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(
        name = "KudosSummaryData",
        description = "Schema to hold KudosSummaryData"
)
public record KudosSummaryData(
        String userName,
        int cantBadges,
        boolean highlight
){}
