package com.equipo5.feelflowapp.service.summary.twelvesteps;

import com.equipo5.feelflowapp.dto.modules.TwelveStepsResponseAvgDto;

import java.util.List;
import java.util.UUID;

public interface SummaryTwelveStepsService {
    List<TwelveStepsResponseAvgDto> getTwelveStepsSurveysSummaryData(Long idModule, UUID idUser);
}
