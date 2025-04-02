package com.equipo5.feelflowapp.service.module.impl;

import com.equipo5.feelflowapp.domain.EnterPrise;
import com.equipo5.feelflowapp.domain.Team;
import com.equipo5.feelflowapp.domain.enumerations.modules.ModuleNames;
import com.equipo5.feelflowapp.domain.enumerations.modules.ModuleState;
import com.equipo5.feelflowapp.domain.enumerations.modules.SurveyStateEnum;
import com.equipo5.feelflowapp.domain.modules.Module;
import com.equipo5.feelflowapp.domain.modules.Survey;
import com.equipo5.feelflowapp.domain.modules.SurveyModule;
import com.equipo5.feelflowapp.domain.modules.kudos.KudosModule;
import com.equipo5.feelflowapp.domain.users.RegularUser;
import com.equipo5.feelflowapp.dto.modules.ModuleSurveyDto;
import com.equipo5.feelflowapp.mappers.modules.ModuleSurveyMapper;
import com.equipo5.feelflowapp.repository.module.ModuleRepository;
import com.equipo5.feelflowapp.repository.module.specification.ModuleSpecification;
import com.equipo5.feelflowapp.repository.survey.SurveyRepository;
import com.equipo5.feelflowapp.repository.team.TeamRepository;
import com.equipo5.feelflowapp.repository.users.regularuser.RegularUserRepository;
import com.equipo5.feelflowapp.service.enterprise.EnterpriseService;
import com.equipo5.feelflowapp.service.module.ModuleService;
import com.equipo5.feelflowapp.service.notification.NotificationService;
import com.equipo5.feelflowapp.service.team.TeamService;
import com.equipo5.feelflowapp.service.users.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

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
    private final EnterpriseService enterpriseService;
    private final TeamService teamService;

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
    public List<SurveyModule> getSurveyModuleByPublishDate(int mes, String name, Team team) {
        Specification<Module> spec = Specification.where(
                        ModuleSpecification.withPublishDate(mes))
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
    public Optional<SurveyModule> getSurveyModuleById(Long id) {

        Optional<Module> surveyModule = moduleRepository.findById(id);

        return surveyModule.map(module -> (SurveyModule) module);

    }

    @Override
    public Optional<SurveyModule> getSurveyModuleById(Long id, UUID idTeam) {
        Optional<Team> team = teamRepository.findById(idTeam);

        return team.flatMap(value -> value.getModules()
                .stream()
                .filter(module -> module.getId().equals(id))
                .map(module -> (SurveyModule) module)
                .findFirst());
    }

    @Override
    public List<SurveyModule> getSurveyModuleByNameAndIdTeam(String name, UUID idTeam) {
        Optional<Team> team = teamRepository.findById(idTeam);
        if (team.isPresent()) {
            return getSurveyModule(name, team.get());
        }
        return List.of();
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
    public List<Module> getAllModules(ModuleNames moduleNames, Boolean isAdmin) {

        if(isAdmin){
            Optional<EnterPrise> enterPrise = enterpriseService.getEnterpriseByCurrentUser();
            if (enterPrise.isPresent()){
                List<Team> teams = enterPrise.get().getTeam();
                return teams.stream()
                        .flatMap(team -> team.getModules().stream())
                        .filter(module -> moduleNames.toString().equals(module.getName()) )
                        .sorted( Comparator.comparing( Module::getDateAndTimeToPublish ))
                        .toList();
            }
        }else{
            Optional<Team> team = teamService.getTeamByCurrentUser();
            if (team.isPresent()){
                 return team.get().getModules()
                         .stream()
                         .filter((module) -> moduleNames.toString().equals(module.getName()) )
                         .sorted( Comparator.comparing( Module::getDateAndTimeToPublish ))
                         .toList();
            }
        }

        return List.of();
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

    @Override
    public List<KudosModule> getModulesBy(String name, Team team) {
        Specification<Module> spec = Specification.where(
                ModuleSpecification.withName(name)
                        .and(ModuleSpecification.withTeam(team))
        );

        return moduleRepository.findAll(spec)
                .stream()
                .map(kudosModule -> (KudosModule) kudosModule )
                .toList();
    }
}
