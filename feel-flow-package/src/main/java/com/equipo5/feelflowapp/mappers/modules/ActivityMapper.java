package com.equipo5.feelflowapp.mappers.modules;

import com.equipo5.feelflowapp.domain.modules.Activity;
import com.equipo5.feelflowapp.dto.modules.ActivityDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper
public interface ActivityMapper {


    @Mapping(source = "question",target = "question")
    @Mapping(source = "answer",target = "answer")
    ActivityDto activityToActivityDto(Activity activity);

    @Mapping(source = "question",target = "question")
    @Mapping(source = "answer",target = "answer")
    Activity activityDtoToActivity(ActivityDto activityDto);

    default List<Activity> getActivitiesFromDtoList(List<ActivityDto> activities){
            return activities.stream()
                    .map(this::activityDtoToActivity)
                    .toList();
    }

}
