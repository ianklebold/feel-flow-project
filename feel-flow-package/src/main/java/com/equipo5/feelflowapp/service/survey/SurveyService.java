package com.equipo5.feelflowapp.service.survey;

import com.equipo5.feelflowapp.domain.enumerations.modules.SurveyStateEnum;
import com.equipo5.feelflowapp.domain.modules.Survey;
import com.equipo5.feelflowapp.domain.modules.SurveyModule;
import com.equipo5.feelflowapp.domain.users.RegularUser;
import com.equipo5.feelflowapp.dto.modules.SurveyDto;

import java.util.List;

public interface SurveyService{

    private static void createSurvey(SurveyModule module, RegularUser user){
        module.getSurveys().add(
                Survey.builder()
                        .surveyModule(module)
                        .surveyStateEnum(SurveyStateEnum.ACTIVE)
                        .regularUser(user)
                        .build()
        );
    }

    default void createSurveis(List<RegularUser> users, SurveyModule module){
        users.forEach(user -> createSurvey(module,user));
    }

    List<SurveyDto> getSurveys();
}
