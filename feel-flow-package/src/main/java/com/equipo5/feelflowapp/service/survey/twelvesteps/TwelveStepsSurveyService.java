package com.equipo5.feelflowapp.service.survey.twelvesteps;

import com.equipo5.feelflowapp.dto.modules.SurveyTwelveStepsResponseDto;
import com.fasterxml.jackson.core.JsonProcessingException;


public interface TwelveStepsSurveyService {
    void completeSurvey(SurveyTwelveStepsResponseDto surveyResponse) throws JsonProcessingException;
}
