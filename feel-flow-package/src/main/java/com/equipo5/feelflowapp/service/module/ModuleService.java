package com.equipo5.feelflowapp.service.module;

import com.equipo5.feelflowapp.domain.enumerations.modules.ModuleNames;
import com.equipo5.feelflowapp.domain.enumerations.modules.ModuleState;
import com.equipo5.feelflowapp.domain.modules.Module;
import com.equipo5.feelflowapp.domain.modules.twelvesteps.TwelveStepsModule;
import com.equipo5.feelflowapp.dto.modules.ModuleListDto;

import java.util.List;
import java.util.Optional;

public interface ModuleService {

    boolean isAnyModuleActive(final String name, final List<Module> modules);

    Optional<Module> getModuleActive(final String name, final List<Module> modules);

    List<ModuleListDto> getModules(final ModuleNames moduleName, final ModuleState moduleState, final String creationOrder);

}
