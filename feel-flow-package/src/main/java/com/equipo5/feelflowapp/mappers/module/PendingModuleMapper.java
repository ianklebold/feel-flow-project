package com.equipo5.feelflowapp.mappers.module;

import com.equipo5.feelflowapp.domain.modules.PendingModule;
import com.equipo5.feelflowapp.dto.module.PendingModuleDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface PendingModuleMapper {

    @Mapping(source = "uuid",target = "uuid")
    @Mapping(source = "regularUser",target = "userDTO")
    @Mapping(source = "moduleState",target = "moduleState")
    @Mapping(source = "dateOfInit",target = "dateOfInit")
    @Mapping(source = "dateOfEnd",target = "dateOfEnd")
    PendingModuleDTO pendingModuleToPendingModuleDto(PendingModule pendingModule);
}
