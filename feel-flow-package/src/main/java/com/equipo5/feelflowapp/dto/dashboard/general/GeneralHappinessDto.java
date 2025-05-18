package com.equipo5.feelflowapp.dto.dashboard.general;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GeneralHappinessDto {
    private String nameHappiness;
    private int percentHappiness;
}
