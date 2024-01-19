package com.equipo5.feelflowapp.mappers.modules;

import com.equipo5.feelflowapp.domain.modules.Activity;
import com.equipo5.feelflowapp.dto.modules.ActivityDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface ActivityMapper {


    @Mapping(source = "question",target = "question")
    @Mapping(source = "answer",target = "answer")
    ActivityDto activityToActivityDto(Activity activity);

}
