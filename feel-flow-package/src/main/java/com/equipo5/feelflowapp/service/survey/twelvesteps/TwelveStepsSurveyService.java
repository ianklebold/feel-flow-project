package com.equipo5.feelflowapp.service.survey.twelvesteps;

import com.equipo5.feelflowapp.domain.modules.Survey;
import com.equipo5.feelflowapp.domain.modules.twelvesteps.TwelveStepsModule;
import com.equipo5.feelflowapp.dto.modules.SurveyTwelveStepsResponseDto;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.List;


public interface TwelveStepsSurveyService {
    void completeSurvey(SurveyTwelveStepsResponseDto surveyResponse) throws JsonProcessingException;

    void forceToCloseSurvey(List<Survey> surveyList);
}
