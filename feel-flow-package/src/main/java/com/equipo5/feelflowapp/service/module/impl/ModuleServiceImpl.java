package com.equipo5.feelflowapp.service.module.impl;

import com.equipo5.feelflowapp.domain.Team;
import com.equipo5.feelflowapp.domain.enumerations.modules.ModuleNames;
import com.equipo5.feelflowapp.domain.enumerations.modules.ModuleState;
import com.equipo5.feelflowapp.domain.enumerations.modules.SurveyStateEnum;
import com.equipo5.feelflowapp.domain.modules.Module;
import com.equipo5.feelflowapp.domain.modules.Survey;
import com.equipo5.feelflowapp.domain.modules.SurveyModule;
import com.equipo5.feelflowapp.domain.users.RegularUser;
import com.equipo5.feelflowapp.dto.modules.ModuleSurveyDto;
import com.equipo5.feelflowapp.mappers.modules.ModuleSurveyMapper;
import com.equipo5.feelflowapp.repository.module.ModuleRepository;
import com.equipo5.feelflowapp.repository.module.specification.ModuleSpecification;
import com.equipo5.feelflowapp.repository.survey.SurveyRepository;
import com.equipo5.feelflowapp.repository.team.TeamRepository;
import com.equipo5.feelflowapp.repository.users.regularuser.RegularUserRepository;
import com.equipo5.feelflowapp.service.module.ModuleService;
import com.equipo5.feelflowapp.service.notification.NotificationService;
import com.equipo5.feelflowapp.service.team.TeamService;
import com.equipo5.feelflowapp.service.users.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.equipo5.feelflowapp.domain.enumerations.modules.ModuleNames.TWELVE_STEPS;

@Service
@RequiredArgsConstructor
public class ModuleServiceImpl implements ModuleService {

    private  final ModuleRepository moduleRepository;

    private final RegularUserRepository regularUserRepository;

    private final UserService userService;

    private final NotificationService notificationService;
    private final SurveyRepository surveyRepository;
    private final TeamRepository teamRepository;
    private final ModuleSurveyMapper moduleSurveyMapper;

    @Override
    public boolean isAnyModuleActive(final String name,final List<Module> modules) {
        return modules.stream()
                .filter(module -> module.getName().equals(name))
                .anyMatch(module -> module.getModuleState().toString().equals(ModuleState.ACTIVE.toString()));
    }

    @Override
    public List<SurveyModule> getSurveyModule(LocalDate creationDate, String name, Team team) {

        Specification<Module> spec = Specification.where(
                ModuleSpecification.withCreationDate(creationDate))
                .and(ModuleSpecification.withName(name))
                .and(ModuleSpecification.withTeam(team));


        return moduleRepository.findAll(spec)
                .stream()
                .map(survey -> (SurveyModule) survey )
                .toList();
    }

    @Override
    public List<SurveyModule> getSurveyModule(String name, Team team) {
        Specification<Module> spec = Specification.where(
                ModuleSpecification.withName(name)
                .and(ModuleSpecification.withTeam(team))
        );

        return moduleRepository.findAll(spec)
                .stream()
                .map(survey -> (SurveyModule) survey )
                .toList();
    }

    @Override
    public Optional<SurveyModule> getSurveyModuleById(Long id, Team team) {

        Optional<Module> surveyModule = moduleRepository.findById(id);

        if(surveyModule.isPresent()) {
            if(surveyModule.get().getTeam().getUuid().equals(team.getUuid())) {
                return Optional.of((SurveyModule) surveyModule.get());
            }
        }

        return Optional.empty();
    }

    @Override
    public Optional<SurveyModule> getSurveyModuleActiveForCurrentUserByModuleName(ModuleNames moduleNames) {

        String currentUserName = this.userService.getUsernameByCurrentUser();
        Optional<RegularUser> regularUser = regularUserRepository.findByUsername( currentUserName );

        if(regularUser.isPresent()) {
            Optional<Module> module = this.moduleRepository.findModuleByTeamAndModuleStateAndName(regularUser.get().getTeam(), ModuleState.ACTIVE, moduleNames.toString());

            if (module.isPresent()){

                SurveyModule surveyModule = (SurveyModule) module.get();
                return Optional.of(surveyModule);

            }

        }

        return Optional.empty();
    }

    @Override
    public void closeModule(ModuleNames moduleNames) {
        Optional<SurveyModule> surveyModule = this.getSurveyModuleActiveForCurrentUserByModuleName(moduleNames);

        if (surveyModule.isPresent()){

            boolean isAllSurveysSolved = surveyModule.get()
                    .getSurveys()
                    .stream()
                    .allMatch(survey -> SurveyStateEnum.FINISHED.equals(survey.getSurveyStateEnum()) || SurveyStateEnum.CLOSED.equals(survey.getSurveyStateEnum()) );

            if(isAllSurveysSolved && ModuleNames.NIKO_NIKO.equals(moduleNames) ){
                boolean isModuleCloseToday = surveyModule.get().getModuleClosedDate().equals( LocalDate.now() );

                if(isModuleCloseToday){
                    surveyModule.get().setModuleState(ModuleState.FINISHED);
                    moduleRepository.save(surveyModule.get());
                    notificationService.sendNotificationModule(
                            surveyModule.get().getTeam().getRegularUsers(),
                            notificationService.generateBodyForCloseModule("Niko Niko"),
                            "Cierre de modulo"
                    );
                }
            }else if(TWELVE_STEPS.equals(moduleNames)){
                surveyModule.get().setModuleState(ModuleState.FINISHED);
                moduleRepository.save(surveyModule.get());
                notificationService.sendNotificationModule(
                        surveyModule.get().getTeam().getRegularUsers(),
                        notificationService.generateBodyForCloseModule("12 pasos de la felicidad"),
                        "Cierre de modulo"
                );
            }

        }

    }

    @Override
    public List<ModuleSurveyDto> getModulesSurveysByTeamIdAndModuleName(ModuleNames moduleNames, UUID teamId ) {
        return this.getModulesByTeamIdAndModuleName(moduleNames, teamId)
                .stream()
                .map(moduleSurveyMapper::moduleToModuleDto)
                .toList();
    }


    @Override
    public List<Module> getModulesByTeamIdAndModuleName( ModuleNames moduleNames, UUID teamId ) {

        Optional<Team> team = teamRepository.findById(teamId);
        if (team.isPresent()) {
            Specification<Module> spec = Specification.where(
                    ModuleSpecification.withName(moduleNames.toString())
                            .and(ModuleSpecification.withTeam(team.get()))
            );

            return moduleRepository.findAll(spec);
        }else{
            return Collections.emptyList();
        }
    }
}
