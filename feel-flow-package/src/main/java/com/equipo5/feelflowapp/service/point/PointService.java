package com.equipo5.feelflowapp.service.point;

import com.equipo5.feelflowapp.domain.modules.Survey;

import java.util.List;

public interface PointService {

    double getPointsByNikoNikoSurvey(Survey survey);
    double getPointsByTwelveStepsSurvey(Survey survey);
    double getTotalOfPointsPossibleNikoNiko(List<Survey> surveys);
    double getTotalOfPointsPossibleNikoNiko(List<Survey> surveys, long sizeTeam);
    double getTotalOfPointsPossibleTwelveSteps(List<Survey> surveys, long sizeTeam);
    double getTotalOfPointsPossibleTwelveSteps(List<Survey> surveys);
}
