package com.equipo5.feelflowapp.service.module;

import com.equipo5.feelflowapp.domain.Team;
import com.equipo5.feelflowapp.domain.enumerations.modules.ModuleNames;
import com.equipo5.feelflowapp.domain.modules.Module;
import com.equipo5.feelflowapp.domain.modules.Survey;
import com.equipo5.feelflowapp.domain.modules.SurveyModule;
import com.equipo5.feelflowapp.dto.modules.ModuleSurveyDto;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ModuleService {

    boolean isAnyModuleActive(final String name, final List<Module> modules);

    List<SurveyModule> getSurveyModule(LocalDate creationDate, String name, Team team);

    List<SurveyModule> getSurveyModuleByPublishDate(int mes, String name, Team team);

    List<SurveyModule> getSurveyModule(String name, Team team);

    Optional<SurveyModule> getSurveyModuleById(Long id);

    Optional<SurveyModule> getSurveyModuleById(Long id, UUID idTeam);

    List<SurveyModule> getSurveyModuleByNameAndIdTeam(String name, UUID idTeam);

    Optional<SurveyModule> getSurveyModuleActiveForCurrentUserByModuleName(ModuleNames moduleNames);

    void closeModule(ModuleNames moduleNames);

    List<Module> getModulesByTeamIdAndModuleName(ModuleNames moduleNames, UUID teamId );

    List<ModuleSurveyDto> getModulesSurveysByTeamIdAndModuleName(ModuleNames moduleNames, UUID teamId );

    List<Module> getAllModules(ModuleNames moduleNames, Boolean isAdmin);
}
