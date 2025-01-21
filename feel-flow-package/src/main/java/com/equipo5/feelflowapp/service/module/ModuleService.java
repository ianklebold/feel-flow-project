package com.equipo5.feelflowapp.service.module;

import com.equipo5.feelflowapp.domain.Team;
import com.equipo5.feelflowapp.domain.enumerations.modules.ModuleNames;
import com.equipo5.feelflowapp.domain.modules.Module;
import com.equipo5.feelflowapp.domain.modules.SurveyModule;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ModuleService {

    boolean isAnyModuleActive(final String name, final List<Module> modules);

    List<SurveyModule> getSurveyModule(LocalDate creationDate, String name, Team team);

    Optional<SurveyModule> getSurveyModuleActiveForCurrentUserByModuleName(ModuleNames moduleNames);

    void closeModule(ModuleNames moduleNames);
}
