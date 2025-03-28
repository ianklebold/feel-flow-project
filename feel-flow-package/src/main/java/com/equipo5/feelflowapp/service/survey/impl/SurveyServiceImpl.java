package com.equipo5.feelflowapp.service.survey.impl;


import com.equipo5.feelflowapp.domain.Team;
import com.equipo5.feelflowapp.domain.enumerations.modules.ModuleNames;
import com.equipo5.feelflowapp.domain.enumerations.modules.SurveyStateEnum;
import com.equipo5.feelflowapp.domain.modules.Module;
import com.equipo5.feelflowapp.domain.modules.Survey;
import com.equipo5.feelflowapp.domain.modules.SurveyModule;
import com.equipo5.feelflowapp.domain.users.RegularUser;
import com.equipo5.feelflowapp.dto.modules.SurveyDto;
import com.equipo5.feelflowapp.exception.notfound.NotFoundException;
import com.equipo5.feelflowapp.mappers.modules.ActivityMapper;
import com.equipo5.feelflowapp.mappers.modules.SurveyMapper;
import com.equipo5.feelflowapp.repository.module.ModuleRepository;
import com.equipo5.feelflowapp.repository.survey.SurveyRepository;
import com.equipo5.feelflowapp.repository.survey.specification.SurveySpecification;
import com.equipo5.feelflowapp.repository.team.TeamRepository;
import com.equipo5.feelflowapp.repository.users.UserRepository;
import com.equipo5.feelflowapp.repository.users.regularuser.RegularUserRepository;
import com.equipo5.feelflowapp.service.module.ModuleService;
import com.equipo5.feelflowapp.service.survey.SurveyService;
import com.equipo5.feelflowapp.service.team.TeamService;
import com.equipo5.feelflowapp.service.users.UserService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Service("SurveyService")
@AllArgsConstructor
public class SurveyServiceImpl implements SurveyService{

    protected final SurveyRepository surveyRepository;

    protected final UserRepository userRepository;

    protected final RegularUserRepository regularUserRepository;

    protected final TeamRepository teamRepository;

    protected final ModuleRepository moduleRepository;

    protected final UserService userService;

    protected final SurveyMapper surveyMapper;

    protected final ModuleService moduleService;

    protected final ActivityMapper activityMapper;

    @Override
    public List<SurveyDto> getSurveys() {
        var username = userService.getUsernameByCurrentUser();

        var regularUser = userRepository.findByUsername(username);

        if (regularUser.isPresent()){
            List<Survey> surveys = surveyRepository.getAllByRegularUserAndSurveyStateEnum((RegularUser) regularUser.get(), SurveyStateEnum.ACTIVE);

            return surveys.stream()
                    .map(surveyMapper::surveyToSurveyDto)
                    .toList();


        }
        return Collections.emptyList();
    }

    @Override
    public List<SurveyDto> getSurveys(SurveyStateEnum surveyState, LocalDate creationDate, String moduleName) {
        var username = userService.getUsernameByCurrentUser();

        var regularUser = userRepository.findByUsername(username);

        if (regularUser.isPresent()){

            var nameTeam = regularUserRepository.findTeamByUsername(username);
            Optional<Team> team = teamRepository.findById(UUID.fromString(nameTeam));

            if (team.isPresent()){
                List<SurveyModule> surveyModule = this.moduleService.getSurveyModule(creationDate,moduleName,team.get());

                if(surveyState != null){
                    return surveyModule.stream()
                            .flatMap(module -> module.getSurveys()
                                    .stream()
                                    .filter(survey -> surveyState.equals(survey.getSurveyStateEnum()) )
                                    .filter(survey -> survey.getRegularUser().getUuid().equals(regularUser.get().getUuid()))
                            )
                            .map(surveyMapper::surveyToSurveyDto)
                            .toList();
                }else{
                    return surveyModule.stream()
                            .flatMap(module -> module.getSurveys()
                                    .stream()
                                    .filter(survey -> survey.getRegularUser().getUuid().equals(regularUser.get().getUuid()))
                            )
                            .map(surveyMapper::surveyToSurveyDto)
                            .toList();
                }

            }

        }
        return Collections.emptyList();
    }

    @Override
    public SurveyDto getLastSurvey() {
        var username = userService.getUsernameByCurrentUser();
        var regularUser = userRepository.findByUsername(username);

        if (regularUser.isPresent()){
            var nameTeam = regularUserRepository.findTeamByUsername(username);
            Optional<Team> team = teamRepository.findById(UUID.fromString(nameTeam));

            if (team.isPresent()){
                List<Module> modules =  this.moduleRepository.findModulesByTeamOrderByIdDescCreationDateDesc(team.get());

                if(!modules.isEmpty()){
                    Survey survey = this.surveyRepository.getSurveyByRegularUserAndSurveyModule(
                            (RegularUser) regularUser.get(),
                            (SurveyModule) modules.stream().findFirst().get()
                    );
                    return surveyMapper.surveyToSurveyDto(survey);
                }
            }

        }
        return null;
    }

    @Override
    public Optional<SurveyDto> getSurveyActiveByModuleName(ModuleNames moduleNames) {
            return getSurveys().stream()
                    .filter(survey -> survey.module().filterTwelveStepsModules(moduleNames))
                    .findFirst();
    }

    @Override
    public Survey getSurveyById(Long id) {
        Optional<Survey> survey = surveyRepository.findById(id);
        if (survey.isEmpty()){
            throw new NotFoundException("No se encontro la encuesta disponible para el modulo");
        }
        return survey.get();
    }

    @Override
    public List<Survey> getSurveysByModule(String moduleName, Team team) {

        if( ModuleNames.TWELVE_STEPS.toString().equals(moduleName) || ModuleNames.NIKO_NIKO.toString().equals(moduleName) ){

            List<SurveyModule> surveyModuleList =  this.moduleService.getSurveyModule(moduleName , team);

            if(ModuleNames.NIKO_NIKO.toString().equals(moduleName)){
                return surveyModuleList.stream()
                        .filter(surveyModule -> surveyModule.getSurveys() != null)
                        .flatMap(module -> module.getSurveys().stream())
                        .toList();
            }

            return surveyModuleList.stream()
                    .flatMap(module -> module.getSurveys().stream())
                    .toList();

        }
        return Collections.emptyList();
    }

    @Override
    public List<Survey> getSurveysByModule(Long id) {

        Optional<SurveyModule> surveyModule =  this.moduleService.getSurveyModuleById(id);

        if (surveyModule.isPresent()){
            return surveyModule.get().getSurveys();
        }

        return List.of();
    }

    @Override
    public List<Survey> getSurveysByModule(Long id, UUID teamId) {
        Optional<SurveyModule> surveyModule =  this.moduleService.getSurveyModuleById(id, teamId);
        if (surveyModule.isPresent()){
            return surveyModule.get().getSurveys();
        }
        return List.of();
    }

    @Override
    public List<Survey> getSurveysByModule(String moduleName, UUID teamId) {
        List<SurveyModule> surveyModules = this.moduleService.getSurveyModuleByNameAndIdTeam(moduleName, teamId);

        return surveyModules.stream()
                .flatMap(module -> module.getSurveys().stream())
                .toList();
    }
}
