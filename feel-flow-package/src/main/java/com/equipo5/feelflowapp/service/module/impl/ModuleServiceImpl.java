package com.equipo5.feelflowapp.service.module.impl;

import com.equipo5.feelflowapp.domain.Team;
import com.equipo5.feelflowapp.domain.enumerations.modules.ModuleState;
import com.equipo5.feelflowapp.domain.enumerations.modules.ModulesTypes;
import com.equipo5.feelflowapp.domain.modules.Module;
import com.equipo5.feelflowapp.domain.modules.PendingModule;
import com.equipo5.feelflowapp.domain.modules.TwelveStepsModule;
import com.equipo5.feelflowapp.domain.users.RegularUser;
import com.equipo5.feelflowapp.dto.module.ModuleDTO;
import com.equipo5.feelflowapp.exception.module.ModuleNotFoundException;
import com.equipo5.feelflowapp.exception.module.TwelveStepsModuleException;
import com.equipo5.feelflowapp.exception.notfound.NotFoundTeamException;
import com.equipo5.feelflowapp.mappers.module.ModuleMapper;
import com.equipo5.feelflowapp.mappers.users.UserMapper;
import com.equipo5.feelflowapp.mappers.users.team.TeamMapper;
import com.equipo5.feelflowapp.repository.modules.TwelveStepModuleRepository;
import com.equipo5.feelflowapp.repository.modules.TwelveStepSurveyRepository;
import com.equipo5.feelflowapp.repository.team.TeamRepository;
import com.equipo5.feelflowapp.repository.users.teamleader.TeamLeaderRepository;
import com.equipo5.feelflowapp.service.module.ModuleService;
import com.equipo5.feelflowapp.service.users.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Service
@RequiredArgsConstructor
public class ModuleServiceImpl implements ModuleService {

    private final UserService userService;

    private final TeamLeaderRepository teamLeaderRepository;

    private final TeamRepository teamRepository;

    private final TwelveStepSurveyRepository twelveStepSurveyRepository;

    private final TwelveStepModuleRepository twelveStepModuleRepository;

    private final ModuleMapper moduleMapper;

    private final UserMapper userMapper;

    private final TeamMapper teamMapper;

    @Override
    public ModuleDTO createModule(ModulesTypes modulesTypes) throws NotFoundTeamException, TwelveStepsModuleException, ModuleNotFoundException {

        if (ModulesTypes.TWELVE_STEPS.toString().equals(modulesTypes.toString())){

            //Obtener el current Team Leader
            String currentUsername = userService.getUsernameByCurrentUser();

            //Obtener su equipo
            String idTeam = teamLeaderRepository.findTeamByUsername(currentUsername);
            Optional<Team> team = teamRepository.findById(UUID.fromString(idTeam));

            if (team.isEmpty()){
               throw new NotFoundTeamException("Not found Team with id : " + idTeam);
            }

            boolean isExistModuleActive = team.get().getModules().stream()
                    .anyMatch(module -> module instanceof TwelveStepsModule && ModuleState.ACTIVE.toString().equals( module.getModuleState().toString() ) );

            if(isExistModuleActive){
                throw new TwelveStepsModuleException("Already exist one module active");
            }

            List<RegularUser> users = team.get().getRegularUsers();

            TwelveStepsModule twelveStepsModule = TwelveStepsModule.builder()
                    .uuid(UUID.randomUUID())
                    .name("Twelve Steps Module For team " + team.get().getName())
                    .dateOfInit(LocalDateTime.now())
                    .team(team.get())
                    .surveys(new ArrayList<>())
                    .pendingModules(new ArrayList<>())
                    .moduleState(ModuleState.ACTIVE).build();

            setSurveysForTwelveSteps(twelveStepsModule);

            users.forEach(user -> {
                        twelveStepsModule.getPendingModules().add(
                                PendingModule.builder()
                                .uuid(UUID.randomUUID())
                                .dateOfInit(LocalDateTime.now())
                                .regularUser(user)
                                .module(twelveStepsModule)
                                .moduleState(ModuleState.ACTIVE)
                                .answers(new ArrayList<>())
                                .build()
                        );
                        twelveStepsModule.getPendingModules().forEach(pending -> pending.getRegularUser().getPendingModule().add(pending));
                    });

            Module module = twelveStepModuleRepository.save(twelveStepsModule);
            ModuleDTO moduleDTO = moduleMapper.moduleToModuleDto(module);
            moduleDTO.setTeamDTO(teamMapper.teamToTeamDto(team.get()));

            return moduleDTO;
        }
        throw new ModuleNotFoundException("Module not found");
    }

    @Override
    public List<ModuleDTO> getModules(ModulesTypes modulesTypes,ModuleState moduleState) {
        String currentUsername = userService.getUsernameByCurrentUser();

        String idTeam = teamLeaderRepository.findTeamByUsername(currentUsername);
        twelveStepModuleRepository.findTwelveModulesByTeamAndState(idTeam,moduleState.toString());

        return null;
    }

    private void setSurveysForTwelveSteps(TwelveStepsModule twelveStepsModule){
        twelveStepsModule.getSurveys().addAll(twelveStepSurveyRepository.findAll());

        twelveStepSurveyRepository.findAll().forEach(
                survey -> {
                    if (CollectionUtils.isEmpty(survey.getSurveyModule())){
                        survey.setSurveyModule(new ArrayList<>());
                    }
                    survey.getSurveyModule().add(twelveStepsModule);
                }
        );

    }


}
