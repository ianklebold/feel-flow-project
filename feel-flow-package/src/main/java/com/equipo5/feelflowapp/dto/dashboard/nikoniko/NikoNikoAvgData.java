package com.equipo5.feelflowapp.dto.dashboard.nikoniko;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.DayOfWeek;

@Schema(
        name = "NikoNikoAvgData",
        description = "Schema to hold niko niko information"
)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NikoNikoAvgData{

    @Schema(description = "Day of week", example = "MONDAY") private DayOfWeek dayOfWeek;
    @Schema(description = "average of response", example = "2.0") private Double avg;
}
