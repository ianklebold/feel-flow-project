package com.equipo5.feelflowapp.mappers.modules;

import com.equipo5.feelflowapp.domain.modules.Survey;
import com.equipo5.feelflowapp.dto.modules.SurveyListDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface SurveyListMapper {
    @Mapping(source = "id",target = "idSurvey")
    @Mapping(source = "surveyStateEnum",target = "surveyState")
    @Mapping(source = "activities",target = "activityList")
    @Mapping(source = "regularUser",target = "regularUserListDto")
    SurveyListDto surveyToSurveyListDto(Survey survey);
}
