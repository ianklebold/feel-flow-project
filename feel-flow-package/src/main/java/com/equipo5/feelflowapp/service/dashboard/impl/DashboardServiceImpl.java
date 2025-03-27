package com.equipo5.feelflowapp.service.dashboard.impl;

import com.equipo5.feelflowapp.constants.module.twelvesteps.QuestionsConstantsTwelveSteps;
import com.equipo5.feelflowapp.domain.Team;
import com.equipo5.feelflowapp.domain.enumerations.modules.ModuleNames;
import com.equipo5.feelflowapp.domain.modules.Survey;
import com.equipo5.feelflowapp.dto.modules.TwelveStepsResponseAvgDto;
import com.equipo5.feelflowapp.dto.team.TeamListDTO;
import com.equipo5.feelflowapp.repository.team.TeamRepository;
import com.equipo5.feelflowapp.service.dashboard.DashboardService;
import com.equipo5.feelflowapp.service.module.twelveSteps.TwelveStepsService;
import com.equipo5.feelflowapp.service.survey.SurveyService;
import com.equipo5.feelflowapp.service.team.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class DashboardServiceImpl implements DashboardService {

    private final SurveyService surveyService;
    private final TeamService teamService;
    private final TeamRepository teamRepository;
    private final TwelveStepsService twelveStepsService;

    @Autowired
    public DashboardServiceImpl(@Qualifier("SurveyService") SurveyService surveyService, TeamService teamService, TeamRepository teamRepository, TwelveStepsService twelveStepsService) {
        this.surveyService = surveyService;
        this.teamService = teamService;
        this.teamRepository = teamRepository;
        this.twelveStepsService = twelveStepsService;
    }

    @Override
    public List<TwelveStepsResponseAvgDto> getTwelveStepsSurveysAveragedData(Long id) {

        //Si no es admin --> Debe ver su propio equipo
        List<TeamListDTO> teamListDTOS = this.teamService.getAllTeams();

        if (teamListDTOS.size() == 1){
            Team team = teamRepository.getReferenceById(teamListDTOS.getFirst().getUuid());
            List<Survey> surveys = new ArrayList<>();

            if(id == null){
                surveys = this.surveyService.getSurveysByModule(ModuleNames.TWELVE_STEPS.toString(), team);
            }else{
                surveys = this.surveyService.getSurveysByModule(id, team);
            }

            return getTwelveStepsResponseAvgDto(surveys);
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
