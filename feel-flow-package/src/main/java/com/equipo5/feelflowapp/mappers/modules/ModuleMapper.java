package com.equipo5.feelflowapp.mappers.modules;

import com.equipo5.feelflowapp.domain.modules.Module;
import com.equipo5.feelflowapp.dto.modules.ModuleDto;
import com.equipo5.feelflowapp.mappers.users.UserMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(uses = UserMapper.class)
public interface ModuleMapper {

    @Mapping(source = "name",target = "name")
    @Mapping(source = "creationDate",target = "creationDate")
    @Mapping(source = "moduleState",target = "moduleState")
    @Mapping(source = "team",target = "team")
    ModuleDto moduleToModuleDto(Module module);
}
