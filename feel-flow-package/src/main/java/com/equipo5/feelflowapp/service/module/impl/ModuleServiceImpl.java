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
import com.equipo5.feelflowapp.domain.modules.nikoniko.NikoNikoModule;
import com.equipo5.feelflowapp.domain.users.RegularUser;
import com.equipo5.feelflowapp.dto.dashboard.general.ParticipationOnModulesDto;
import com.equipo5.feelflowapp.dto.modules.ModuleSurveyDto;
import com.equipo5.feelflowapp.dto.team.TeamListDTO;
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

import static com.equipo5.feelflowapp.domain.enumerations.modules.ModuleNames.*;

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
    public List<SurveyModule> getSurveyModuleForCurrentUserByModuleName(ModuleNames moduleNames, UUID idUser) {

        Optional<RegularUser> regularUser = regularUserRepository.findByUuid( idUser );

        if(regularUser.isPresent()) {
            List<Module> modules = this.moduleRepository.findAllByTeamAndName(regularUser.get().getTeam(), moduleNames.toString());

            if (!modules.isEmpty()){

                return modules.stream()
                        .map(module -> (SurveyModule) module).toList();

            }

        }

        return List.of();
    }

    @Override
    public SurveyModule closeModule(ModuleNames moduleNames) {
        Optional<SurveyModule> surveyModule = this.getSurveyModuleActiveForCurrentUserByModuleName(moduleNames);

        if (surveyModule.isPresent()){

            boolean isAllSurveysSolved = surveyModule.get()
                    .getSurveys()
                    .stream()
                    .allMatch(survey -> SurveyStateEnum.FINISHED.equals(survey.getSurveyStateEnum()) || SurveyStateEnum.CLOSED.equals(survey.getSurveyStateEnum()) );

            if(isAllSurveysSolved){
                if(NIKO_NIKO.equals(moduleNames) ){
                    boolean isModuleCloseToday = surveyModule.get().getModuleClosedDate().equals( LocalDate.now() );

                    if(isModuleCloseToday){
                        surveyModule.get().setModuleState(ModuleState.FINISHED);
                        SurveyModule surveyModuleSaved = moduleRepository.save(surveyModule.get());
                        notificationService.sendNotificationModule(
                                surveyModule.get().getTeam().getRegularUsers(),
                                notificationService.generateBodyForCloseModule("Niko Niko"),
                                "Cierre de modulo"
                        );
                        return surveyModuleSaved;
                    }
                }else if(TWELVE_STEPS.equals(moduleNames)){
                    surveyModule.get().setModuleState(ModuleState.FINISHED);
                    SurveyModule surveyModuleSaved = moduleRepository.save(surveyModule.get());
                    notificationService.sendNotificationModule(
                            surveyModule.get().getTeam().getRegularUsers(),
                            notificationService.generateBodyForCloseModule("12 pasos de la felicidad"),
                            "Cierre de modulo"
                    );
                    return surveyModuleSaved;
                }
            }
        }
        return null;
    }

    @Override
    public List<ModuleSurveyDto> getModulesSurveysByTeamIdAndModuleName(ModuleNames moduleNames, UUID teamId ) {
        return this.getModulesByTeamIdAndModuleName(moduleNames, teamId)
                .stream()
                .map(moduleSurveyMapper::moduleToModuleDto)
                .toList();
    }

    @Override
    public List<ModuleSurveyDto> getModulesSurveysByTeamAndModuleName(ModuleNames moduleNames, Team team ) {
        return this.getModulesByTeamAndModuleName(moduleNames, team)
                .stream()
                .map(moduleSurveyMapper::moduleToModuleDto)
                .toList();
    }

    private List<SurveyModule>  getModulesSurveyByTeamAndModuleNameAndModuleState(ModuleNames moduleNames, Team team, ModuleState moduleState) {
        return this.getModulesByTeamAndModuleName(moduleNames, team)
                .stream()
                .filter( module -> moduleState.equals( module.getModuleState() ) )
                .map( module -> (SurveyModule) module)
                .toList();
    }

    private List<KudosModule>  getKudosModuleByTeamAndModuleNameAndModuleState(ModuleNames moduleNames, Team team, ModuleState moduleState) {
        return this.getModulesByTeamAndModuleName(moduleNames, team)
                .stream()
                .filter( module -> moduleState.equals( module.getModuleState() ) )
                .map( module ->  (KudosModule) module )
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

    public ParticipationOnModulesDto getParticipationOnModulesDto(){
        ParticipationOnModulesDto dto = new ParticipationOnModulesDto();
        dto.setName("Participation en Modulos");

        //Todos se refieren a modulos activos.

        //Kudos la cantidad de tableros del modulo en estado cerrado.
        //Niko Niko la cantidad de usuarios que respondieron al menos una encuesta.
        //12 Pasos de la felicidad, cantidad de encuestas terminadas.

        return null;

    }

    @Override
    public List<Module> getAllModules() {
        List<Team> teams = this.teamService.getAllTeamsEntities();

        if(teams.size() == 1){

            Team team = teams.get(0);
            return team.getModules()
                    .stream()
                    .toList();

        }else if (teams.size() > 1){

            return teams.stream()
                    .flatMap(team -> team.getModules().stream())
                    .toList();

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
    public List<Module> getModulesByTeamAndModuleName( ModuleNames moduleNames, Team team ) {

        if (team != null) {
            Specification<Module> spec = Specification.where(
                    ModuleSpecification.withName(moduleNames.toString())
                            .and(ModuleSpecification.withTeam(team))
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

    @Override
    public double getGeneralPercentOfModulesCompleted() {
        List<Team> teams = teamService.getAllTeamsEntities();

        int numberTotalOfModulesActives = 0;
        int numberTotalOfMembers = teamService.getNumberOfMembersOfTeam(teams);
        List<SurveyModule> modulesTwelveSteps = new ArrayList<>();
        List<SurveyModule> modulesNikoNiko = new ArrayList<>();
        List<KudosModule> kudosModules = new ArrayList<>();


        teams.forEach(
                team -> {
                    modulesTwelveSteps.addAll( getModulesSurveyByTeamAndModuleNameAndModuleState(ModuleNames.TWELVE_STEPS, team, ModuleState.ACTIVE) );
                    modulesNikoNiko.addAll( getModulesSurveyByTeamAndModuleNameAndModuleState(NIKO_NIKO, team, ModuleState.ACTIVE) );
                    kudosModules.addAll( getKudosModuleByTeamAndModuleNameAndModuleState( KUDOS, team, ModuleState.ACTIVE ) );
                }
        );
        double percentOfCompletedTwelveStepsModule = getPercentOfCompletedModules( modulesTwelveSteps, numberTotalOfMembers );
        double percentOfCompletedNikoNikoModule = getPercentOfCompletedModules( modulesNikoNiko, numberTotalOfMembers );
        double percentOfCompletedKudosModule = getPercentOfCompletedKudosModule( kudosModules, numberTotalOfMembers );

        if ( !modulesTwelveSteps.isEmpty() ){
            numberTotalOfModulesActives = numberTotalOfModulesActives + 1;
        }
        if ( !modulesNikoNiko.isEmpty() ){
            numberTotalOfModulesActives = numberTotalOfModulesActives + 1;
        }
        if ( !kudosModules.isEmpty() ){
            numberTotalOfModulesActives = numberTotalOfModulesActives + 1;
        }

        return (percentOfCompletedTwelveStepsModule + percentOfCompletedNikoNikoModule + percentOfCompletedKudosModule) / numberTotalOfModulesActives;
    }

    private double getPercentOfCompletedModules(List<SurveyModule> modulesTwelveSteps, int numberOfMembers){
         Optional<Double> percentSurveyCompleted = modulesTwelveSteps.stream().map(
                module -> {
                    long countOfSurveysCompleted =  module.getSurveys().stream()
                            .filter(
                                    survey -> SurveyStateEnum.CLOSED.equals(survey.getSurveyStateEnum()) || SurveyStateEnum.FINISHED.equals(survey.getSurveyStateEnum())
                            ).count();
                    return (double) ( countOfSurveysCompleted / module.getSurveys().size() );
                }
         ).reduce(Double::sum);

         if (percentSurveyCompleted.isPresent()){
             int numberOfTeams = modulesTwelveSteps.size();

             return numberOfTeams * numberOfMembers / percentSurveyCompleted.get() * ( (double) 1 / modulesTwelveSteps.size() );
         }
         return 0;
    }

    private double getPercentOfCompletedKudosModule(List<KudosModule> kudosModules, int numberOfMembers){
         Optional<Double> percentKudosTableCompleted = kudosModules.stream().map(
                module -> {
                    long countOfTablesCompleted =  module.getTableBadge().stream()
                            .filter(
                                    table -> table.getTableBadgeClosedDate() != null
                            ).count();
                    return (double) ( countOfTablesCompleted / module.getTableBadge().size() );
                }
         ).reduce(Double::sum);

         if (percentKudosTableCompleted.isPresent()){
             int numberOfTeams = kudosModules.size();

             return numberOfTeams * numberOfMembers / percentKudosTableCompleted.get() * ( (double) 1 / kudosModules.size() );
         }
         return 0;
    }



}
