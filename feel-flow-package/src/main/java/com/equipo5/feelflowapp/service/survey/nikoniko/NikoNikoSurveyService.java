package com.equipo5.feelflowapp.service.survey.nikoniko;

import com.equipo5.feelflowapp.domain.modules.SurveyModule;
import com.equipo5.feelflowapp.domain.users.RegularUser;
import com.equipo5.feelflowapp.dto.modules.SurveyAvailableNikoNikoReponseDto;
import com.equipo5.feelflowapp.dto.response.ResponseDto;

import java.util.List;

public interface NikoNikoSurveyService {
    public void createSurveis(List<RegularUser> users, SurveyModule module);

    SurveyAvailableNikoNikoReponseDto getSurveyAvailable();

    void completeSurvey(SurveyAvailableNikoNikoReponseDto surveyResponse);

}
