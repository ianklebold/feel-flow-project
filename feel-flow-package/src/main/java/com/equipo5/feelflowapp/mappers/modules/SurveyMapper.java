package com.equipo5.feelflowapp.mappers.modules;

import com.equipo5.feelflowapp.domain.modules.Survey;
import com.equipo5.feelflowapp.dto.modules.SurveyDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(uses = {ActivityMapper.class, ModuleMapper.class})
public interface SurveyMapper {

    @Mapping(source = "id",target = "idSurvey")
    @Mapping(source = "surveyStateEnum",target = "surveyState")
    @Mapping(source = "regularUser",target = "regularUser")
    @Mapping(source = "activities",target = "activityList")
    @Mapping(source = "surveyModule",target = "module")
    SurveyDto surveyToSurveyDto(Survey survey);


}
