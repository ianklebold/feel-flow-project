package com.equipo5.feelflowapp.service.dashboard;

import com.equipo5.feelflowapp.domain.enumerations.modules.ModuleNames;
import com.equipo5.feelflowapp.dto.dashboard.TeamAndModulesDto;
import com.equipo5.feelflowapp.dto.modules.ModuleDto;
import com.equipo5.feelflowapp.dto.modules.TwelveStepsResponseAvgDto;

import java.util.List;

public interface DashboardService {
    List<TwelveStepsResponseAvgDto> getTwelveStepsSurveysAveragedData(Long id);
    List<TeamAndModulesDto> getTeamsAndModulesData(boolean isAdmin, ModuleNames nameModule);
}
