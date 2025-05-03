package com.equipo5.feelflowapp.dto.dashboard.general;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GeneralSummaryDto {
    private String emotionalState;
    private GeneralHappinessDto generalHappiness;
    private KudosSentDto kudosSent;
    private ParticipationOnModulesDto participationOnModules;
}
