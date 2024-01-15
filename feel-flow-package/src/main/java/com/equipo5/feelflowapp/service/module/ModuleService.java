package com.equipo5.feelflowapp.service.module;

import com.equipo5.feelflowapp.domain.modules.Module;

import java.util.List;

public interface ModuleService {

    boolean isAnyModuleActive(final String name, final List<Module> modules);

}
