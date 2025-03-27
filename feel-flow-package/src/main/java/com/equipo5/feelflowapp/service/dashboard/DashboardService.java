package com.equipo5.feelflowapp.service.dashboard;

import com.equipo5.feelflowapp.dto.modules.TwelveStepsResponseAvgDto;

import java.util.List;
import java.util.UUID;

public interface DashboardService {
    List<TwelveStepsResponseAvgDto> getTwelveStepsSurveysAveragedData(Long id);
}
