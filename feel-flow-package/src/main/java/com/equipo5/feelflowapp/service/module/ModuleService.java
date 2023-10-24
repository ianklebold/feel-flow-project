package com.equipo5.feelflowapp.service.module;

import com.equipo5.feelflowapp.domain.enumerations.modules.ModulesTypes;
import com.equipo5.feelflowapp.dto.module.ModuleDTO;


public interface ModuleService {

    ModuleDTO createModule(ModulesTypes modulesTypes);

}
