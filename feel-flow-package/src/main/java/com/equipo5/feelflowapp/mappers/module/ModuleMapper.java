package com.equipo5.feelflowapp.mappers.module;

import com.equipo5.feelflowapp.domain.modules.Module;
import com.equipo5.feelflowapp.dto.module.ModuleDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface ModuleMapper {

    @Mapping(source = "uuid",target = "uuid")
    @Mapping(source = "name",target = "name")
    @Mapping(source = "moduleState",target = "moduleState")
    @Mapping(source = "team",target = "teamDTO")
    @Mapping(source = "pendingModules",target = "pendingModuleDTO")
    @Mapping(source = "dateOfInit",target = "dateOfInit")
    @Mapping(source = "dateOfEnd",target = "dateOfEnd")
    ModuleDTO moduleToModuleDto(Module module);
}
