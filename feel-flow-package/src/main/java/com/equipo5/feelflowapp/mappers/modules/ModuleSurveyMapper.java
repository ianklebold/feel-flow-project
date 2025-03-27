package com.equipo5.feelflowapp.mappers.modules;


import com.equipo5.feelflowapp.domain.modules.Module;
import com.equipo5.feelflowapp.dto.modules.ModuleSurveyDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface ModuleSurveyMapper {
    @Mapping(source = "name",target = "name")
    @Mapping(source = "id",target = "id")
    @Mapping(source = "creationDate",target = "creationDate")
    @Mapping(source = "moduleState",target = "moduleState")
    @Mapping(source = "dateAndTimeToPublish",target = "dateAndTimeToPublish")
    @Mapping(source = "dateAndTimeToClose",target = "dateAndTimeToClose")
    ModuleSurveyDto moduleToModuleDto(Module module);
}
