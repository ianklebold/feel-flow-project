package com.equipo5.feelflowapp.service.module;

import com.equipo5.feelflowapp.domain.enumerations.modules.ModuleState;
import com.equipo5.feelflowapp.domain.enumerations.modules.ModulesTypes;
import com.equipo5.feelflowapp.dto.module.ModuleDTO;
import com.equipo5.feelflowapp.exception.module.ModuleNotFoundException;
import com.equipo5.feelflowapp.exception.module.TwelveStepsModuleException;
import com.equipo5.feelflowapp.exception.notfound.NotFoundTeamException;

import java.util.List;


public interface ModuleService {

    ModuleDTO createModule(ModulesTypes modulesTypes) throws NotFoundTeamException, TwelveStepsModuleException, ModuleNotFoundException;

    List<ModuleDTO> getModules(ModulesTypes modulesTypes,ModuleState moduleState);
}
