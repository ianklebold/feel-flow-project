package com.equipo5.feelflowapp.dto.modules;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Schema(
        name = "TwelveStepsResponseAvgDto",
        description = "Schema to hold average of 12 steps category"
)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TwelveStepsResponseAvgDto{
    private String categoryName;
    private double average;
}