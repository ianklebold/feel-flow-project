package com.equipo5.feelflowapp.service.module;

import com.equipo5.feelflowapp.domain.Team;
import com.equipo5.feelflowapp.domain.modules.Module;
import com.equipo5.feelflowapp.domain.modules.SurveyModule;

import java.time.LocalDate;
import java.util.List;

public interface ModuleService {

    boolean isAnyModuleActive(final String name, final List<Module> modules);

    List<SurveyModule> getSurveyModule(LocalDate creationDate, String name, Team team);

}
