package com.equipo5.feelflowapp.service.dashboard.impl;

import com.equipo5.feelflowapp.constants.module.twelvesteps.QuestionsConstantsTwelveSteps;
import com.equipo5.feelflowapp.domain.Team;
import com.equipo5.feelflowapp.domain.enumerations.modules.ModuleNames;
import com.equipo5.feelflowapp.domain.modules.Module;
import com.equipo5.feelflowapp.domain.modules.Survey;
import com.equipo5.feelflowapp.domain.modules.SurveyModule;
import com.equipo5.feelflowapp.dto.dashboard.TeamAndModulesDto;
import com.equipo5.feelflowapp.dto.modules.ModuleAndUsersDto;
import com.equipo5.feelflowapp.dto.modules.TwelveStepsResponseAvgDto;
import com.equipo5.feelflowapp.dto.team.TeamDTO;
import com.equipo5.feelflowapp.dto.team.TeamListDTO;
import com.equipo5.feelflowapp.mappers.modules.ModuleMapper;
import com.equipo5.feelflowapp.mappers.modules.SurveyMapper;
import com.equipo5.feelflowapp.mappers.users.UserMapper;
import com.equipo5.feelflowapp.repository.team.TeamRepository;
import com.equipo5.feelflowapp.service.dashboard.DashboardService;
import com.equipo5.feelflowapp.service.module.ModuleService;
import com.equipo5.feelflowapp.service.module.twelveSteps.TwelveStepsService;
import com.equipo5.feelflowapp.service.survey.SurveyService;
import com.equipo5.feelflowapp.service.team.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class DashboardServiceImpl implements DashboardService {

    private final SurveyService surveyService;
    private final TeamService teamService;
    private final TeamRepository teamRepository;
    private final TwelveStepsService twelveStepsService;
    private final ModuleService moduleService;

    private final ModuleMapper moduleMapper;
    private final UserMapper userMapper;

    @Autowired
    public DashboardServiceImpl(@Qualifier("SurveyService") SurveyService surveyService, TeamService teamService, TeamRepository teamRepository, TwelveStepsService twelveStepsService, ModuleService moduleService, ModuleMapper moduleMapper, UserMapper userMapper) {
        this.surveyService = surveyService;
        this.teamService = teamService;
        this.teamRepository = teamRepository;
        this.twelveStepsService = twelveStepsService;
        this.moduleService = moduleService;
        this.moduleMapper = moduleMapper;
        this.userMapper = userMapper;
    }

    @Override
    public List<TwelveStepsResponseAvgDto> getTwelveStepsSurveysAveragedData() {

        //Si no es admin --> Debe ver su propio equipo
        List<TeamListDTO> teamListDTOS = this.teamService.getAllTeams();

        if (teamListDTOS.size() == 1){
            Team team = teamRepository.getReferenceById(teamListDTOS.getFirst().getUuid());
            List<Survey> surveys = new ArrayList<>();

            surveys = this.surveyService.getSurveysByModule(ModuleNames.TWELVE_STEPS.toString(), team);

            return getTwelveStepsResponseAvgDto(surveys);
        } else if (teamListDTOS.size() > 1) {

            List<Team> teams = teamRepository.findAllById(
                    teamListDTOS.stream().map(TeamListDTO::getUuid).collect(Collectors.toList())
            );
            List<Survey> surveys = new ArrayList<>();
            teams.forEach(team -> {
                        surveys.addAll(this.surveyService.getSurveysByModule(ModuleNames.TWELVE_STEPS.toString(), team));
                    });
            return getTwelveStepsResponseAvgDto(surveys);
        }

        return List.of();
    }

    @Override
    public List<TeamAndModulesDto> getTeamsAndModulesData(boolean isAdmin,ModuleNames nameModule) {
        List<TeamAndModulesDto> teamAndModulesDtos = new ArrayList<>();
        List<TeamDTO> teamDto = this.teamService.getTeamsByRole(isAdmin);
        teamDto.forEach(team ->
            teamAndModulesDtos.add(
                    new TeamAndModulesDto(
                            team,
                            moduleService.getModulesSurveysByTeamIdAndModuleName(ModuleNames.TWELVE_STEPS, team.getUuid())
                    )
            )
        );

        return teamAndModulesDtos;
    }

    @Override
    public List<ModuleAndUsersDto> getModuleAndUsersData(ModuleNames nameModule, Boolean isAdmin) {

        //Si es admin entonces devolver todos los modulos de toda la empresa con todos sus usuarios.
        List<Module> modules = moduleService.getAllModules(ModuleNames.TWELVE_STEPS, isAdmin);
        //Si no es admin entonces devolver todos los modulos del equipo
        if ( !modules.isEmpty() ){
            return modules.stream()
                    .map( module ->
                            new ModuleAndUsersDto(
                                    moduleMapper.moduleToSimpleModuleDto(module),
                                    module.getTeam().getRegularUsers().stream().map(userMapper::userToUserDto).toList()
                            )
                    )
                    .toList();
        }


        return List.of();
    }

    @Override
    public List<TwelveStepsResponseAvgDto> getTwelveStepsSurveysSummaryData(Long idModule, UUID idUser) {
        List<TwelveStepsResponseAvgDto> twelveStepsResponseAvgDtos = new ArrayList<>();
        if(idModule != null ){
            if(idUser != null ){
                // Devolver resultados de un usuario
                Optional<SurveyModule> surveyModule = moduleService.getSurveyModuleById(idModule);
                if (surveyModule.isPresent()){
                     Optional<Survey> surveyOptional = surveyModule.get().getSurveys()
                             .stream()
                             .filter(survey -> survey.getRegularUser().getUuid().equals(idUser))
                             .findFirst();

                     if (surveyOptional.isPresent()){
                         for (int i = 0; i < 12; i++){
                             twelveStepsResponseAvgDtos.add(
                                     new TwelveStepsResponseAvgDto(
                                             QuestionsConstantsTwelveSteps.QUESTIONS_CATEGORY_TWELVE_STEPS.get(i),
                                             twelveStepsService.getValueForAnswer(surveyOptional.get().getActivities().get(i).getAnswer())
                                     )
                             );
                         }
                     }

                }
                return twelveStepsResponseAvgDtos;
            }else{
                Optional<SurveyModule> surveyModule = moduleService.getSurveyModuleById(idModule);
                if (surveyModule.isPresent()){
                    return getTwelveStepsResponseAvgDto(surveyModule.get().getSurveys());
                }
            }
        }
        return List.of();
    }

    private List<TwelveStepsResponseAvgDto> getTwelveStepsResponseAvgDto(List<Survey> surveys) {

        List<TwelveStepsResponseAvgDto> twelveStepsResponseAvgDtos = new ArrayList<>();

        for (int i = 0; i < 12; i++) {
            twelveStepsResponseAvgDtos.add(new TwelveStepsResponseAvgDto(
                    QuestionsConstantsTwelveSteps.QUESTIONS_CATEGORY_TWELVE_STEPS.get(i),
                    0d
            ));
        }

        surveys.forEach(survey -> {
            for (int i = 0; i < 12; i++) {
                twelveStepsResponseAvgDtos.get(i).setAverage(
                        twelveStepsResponseAvgDtos.get(i).getAverage() + twelveStepsService.getValueForAnswer(survey.getActivities().get(i).getAnswer())
                );
            }
        });

        surveys.forEach(survey -> {
            for (int i = 0; i < 12; i++) {
                twelveStepsResponseAvgDtos.get(i).setAverage(
                        twelveStepsResponseAvgDtos.get(i).getAverage()/ getNumberOfSurveysWithActivitiesCompleted(surveys, i)
                );
            }
        });

        return twelveStepsResponseAvgDtos;
    }

    private double getNumberOfSurveysWithActivitiesCompleted(List<Survey> surveys, int activityNumber) {
        return (double) surveys.stream()
                .filter(survey -> !survey.getActivities().get(activityNumber).getAnswer().isEmpty())
                .count();
    }

    //Si es admin --> Debe ver el de todos los equipos con las posibilidad de ver el de uno en especifico.

}
