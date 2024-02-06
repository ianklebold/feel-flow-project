package com.equipo5.feelflowapp.mappers.modules.custom;

import com.equipo5.feelflowapp.domain.modules.Module;
import com.equipo5.feelflowapp.dto.modules.ModuleListDto;

import java.util.List;

public interface ModuleTwelveStepsMapperCustom {
    void mapModuleToTwelveStepsModule(List<ModuleListDto> modulesDtos, List<Module> modules);
}
