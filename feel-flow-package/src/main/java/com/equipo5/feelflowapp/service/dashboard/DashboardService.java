package com.equipo5.feelflowapp.service.dashboard;

import com.equipo5.feelflowapp.domain.enumerations.modules.ModuleNames;
import com.equipo5.feelflowapp.domain.modules.Survey;
import com.equipo5.feelflowapp.dto.dashboard.TeamAndModulesDto;
import com.equipo5.feelflowapp.dto.dashboard.kudos.KudosSummaryData;
import com.equipo5.feelflowapp.dto.dashboard.nikoniko.NikoNikoSummaryData;
import com.equipo5.feelflowapp.dto.modules.ModuleAndUsersDto;
import com.equipo5.feelflowapp.dto.modules.TwelveStepsResponseAvgDto;

import java.util.List;
import java.util.UUID;

public interface DashboardService {
    List<TwelveStepsResponseAvgDto> getTwelveStepsSurveysAveragedData();
    List<TwelveStepsResponseAvgDto> getTwelveStepsSurveysAveragedDataForCurrentUser();
    List<TeamAndModulesDto> getTeamsAndModulesData(boolean isAdmin, ModuleNames nameModule);
    List<ModuleAndUsersDto> getModuleAndUsersData(ModuleNames nameModule, Boolean isAdmin);
    NikoNikoSummaryData getEmotionalTrendDataAvg();
    List<KudosSummaryData> getKudosData();
    List<TwelveStepsResponseAvgDto> getTwelveStepsResponseAvgDto(List<Survey> surveys);
    List<TwelveStepsResponseAvgDto> getTwelveStepsSurveysAveragedDataBySurvey(Survey survey);
}
