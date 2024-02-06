package com.equipo5.feelflowapp.mappers.modules;

import com.equipo5.feelflowapp.domain.modules.Module;
import com.equipo5.feelflowapp.dto.modules.ModuleListDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface ModuleListMapper {

    @Mapping(source = "id",target = "id")
    @Mapping(source = "name",target = "name")
    @Mapping(source = "creationDate",target = "creationDate")
    @Mapping(source = "moduleClosedDate",target = "closeDate")
    @Mapping(source = "moduleState",target = "moduleState")
    ModuleListDto moduleToModuleListDto(Module module);

}
