package com.equipo5.feelflowapp.dto.dashboard.nikoniko;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

@Schema(
        name = "NikoNikoSummaryData",
        description = "Schema to hold NikoNikoAvgData"
)
public record NikoNikoSummaryData(
    List<NikoNikoAvgData>  nikoAvgDataStartOfDay,
    List<NikoNikoAvgData>  nikoAvgDataEndOfDay
) {}
