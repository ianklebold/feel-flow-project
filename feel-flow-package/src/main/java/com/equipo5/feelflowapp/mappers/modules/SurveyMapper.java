package com.equipo5.feelflowapp.mappers.modules;

import com.equipo5.feelflowapp.domain.modules.Survey;
import com.equipo5.feelflowapp.dto.modules.SurveyDto;
import com.equipo5.feelflowapp.service.utils.dateservice.DateUtils;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Mapper(uses = {ActivityMapper.class, ModuleMapper.class})
public interface SurveyMapper {

    @Mapping(source = "id",target = "idSurvey")
    @Mapping(source = "surveyStateEnum",target = "surveyState")
    @Mapping(source = "regularUser",target = "regularUser")
    @Mapping(source = "activities",target = "activityList")
    @Mapping(source = "surveyModule",target = "module")
    @Mapping(source = "survey.surveyModule.dateAndTimeToPublish", target = "isAfterTheDateOfPublic", qualifiedByName = "isAfterTheDateOfPublic")
    @Mapping(source = "survey.surveyModule.dateAndTimeToClose", target = "isBeforeTheDateOfClose", qualifiedByName = "isBeforeTheDateOfClose")
    SurveyDto surveyToSurveyDto(Survey survey);

    @Named("isAfterTheDateOfPublic")
    public static boolean isAfterTheDateOfPublic(Timestamp dateAndTimeToPublish){
        return DateUtils.isAfterToToday( dateAndTimeToPublish  );
    }

    @Named("isBeforeTheDateOfClose")
    public static boolean isBeforeTheDateOfClose(Timestamp dateAndTimeToClose){
        return DateUtils.isBeforeToToday(dateAndTimeToClose);
    }
}
