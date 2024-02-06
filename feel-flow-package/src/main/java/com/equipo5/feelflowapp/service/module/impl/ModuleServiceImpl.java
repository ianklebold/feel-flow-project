package com.equipo5.feelflowapp.service.module.impl;

import com.equipo5.feelflowapp.domain.Team;
import com.equipo5.feelflowapp.domain.enumerations.modules.ModuleNames;
import com.equipo5.feelflowapp.domain.enumerations.modules.ModuleState;
import com.equipo5.feelflowapp.domain.enumerations.modules.SurveyStateEnum;
import com.equipo5.feelflowapp.domain.modules.Module;
import com.equipo5.feelflowapp.domain.modules.twelvesteps.TwelveStepsModule;
import com.equipo5.feelflowapp.dto.modules.ModuleListDto;
import com.equipo5.feelflowapp.mappers.modules.ModuleListMapper;
import com.equipo5.feelflowapp.mappers.modules.SurveyListMapper;
import com.equipo5.feelflowapp.mappers.modules.custom.ModuleTwelveStepsMapperCustom;
import com.equipo5.feelflowapp.repository.module.ModuleRepository;
import com.equipo5.feelflowapp.repository.survey.SurveyRepository;
import com.equipo5.feelflowapp.repository.team.TeamRepository;
import com.equipo5.feelflowapp.repository.users.teamleader.TeamLeaderRepository;
import com.equipo5.feelflowapp.service.module.ModuleService;
import com.equipo5.feelflowapp.service.users.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class ModuleServiceImpl implements ModuleService {

    private final SurveyRepository surveyRepository;

    private final TeamLeaderRepository teamLeaderRepository;

    private final TeamRepository teamRepository;

    private final ModuleRepository moduleRepository;

    private final UserService userService;

    private final SurveyListMapper surveyListMapper;

    private final ModuleListMapper moduleListMapper;

    private final ModuleTwelveStepsMapperCustom moduleTwelveStepsMapperCustom;

    @Override
    public boolean isAnyModuleActive(final String name,final List<Module> modules) {
        return modules.stream()
                .filter(module -> module.getName().equals(name))
                .anyMatch(module -> module.getModuleState().toString().equals(ModuleState.ACTIVE.toString()));
    }

    @Override
    public Optional<Module> getModuleActive(String name, List<Module> modules) {

        return modules.stream()
                .filter(module -> module.getName().equals(name))
                .findFirst();
    }

    @Override
    public List<ModuleListDto> getModules(ModuleNames moduleName, ModuleState moduleState, String creationOrder) {
        var username = userService.getUsernameByCurrentUser();
        String teamId = teamLeaderRepository.findTeamByUsername(username);

        Optional<Team> team = teamRepository.findById(UUID.fromString(teamId));

        if (team.isPresent()){
            List<Module> modules = new ArrayList<>();
            if (moduleName != null && moduleState == null){
                //Buscar por moduleName
                modules = findModulesByModuleName(moduleName.toString(),creationOrder);
            }else if (moduleName == null && moduleState != null){
                //Buscar por moduleState
                modules = findModulesByState(moduleState.toString(),creationOrder);
            } else if (moduleName != null && moduleState != null) {
                //Buscar por ambos
                modules = findModulesByModuleStateAndName(moduleState.toString(),moduleName.toString(),creationOrder);
            }else {
                //Retornar todos
                modules = findAllModules(creationOrder);
            }
            var modulesDtos = modules.stream()
                    .map(moduleListMapper::moduleToModuleListDto).toList();

            moduleTwelveStepsMapperCustom.mapModuleToTwelveStepsModule(modulesDtos,modules);
            return modulesDtos;
        }

        return Collections.emptyList();
    }


    private List<Module> findModulesByModuleName(String moduleName, String creationOrder){
        if (creationOrder.equals("true")){
            return moduleRepository.findAllModulesByNameWithCreationDateOrder(moduleName);
        }
        return moduleRepository.findAllModulesByNameWithoutOrder(moduleName);
    }

    private List<Module> findModulesByState(String moduleState, String creationOrder){
        if (creationOrder.equals("true")){
            return moduleRepository.findAllModulesByStateWithCreationDateOrder(ModuleState.valueOf(moduleState));
        }
        return moduleRepository.findAllModulesByStateWithoutOrder(ModuleState.valueOf(moduleState));
    }

    private List<Module> findModulesByModuleStateAndName(String moduleState, String moduleName,String creationOrder){
        if (creationOrder.equals("true")){
            return moduleRepository.findAllModulesByStateAndNameWithCreationDateOrder(ModuleState.valueOf(moduleState),moduleName);
        }
        return moduleRepository.findAllModulesByStateAndNameWithoutOrder(ModuleState.valueOf(moduleState),moduleName);
    }

    private List<Module> findAllModules(String creationOrder){
        if (creationOrder.equals("true")){
            Sort sortElements = Sort.by(Sort.Order.asc("creationDate"));
            return moduleRepository.findAll(sortElements);
        }
        return moduleRepository.findAll();
    }

}
