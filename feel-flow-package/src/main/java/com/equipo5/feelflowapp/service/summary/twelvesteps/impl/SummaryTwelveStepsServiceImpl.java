package com.equipo5.feelflowapp.service.summary.twelvesteps.impl;

import com.equipo5.feelflowapp.constants.module.twelvesteps.QuestionsConstantsTwelveSteps;
import com.equipo5.feelflowapp.domain.modules.Survey;
import com.equipo5.feelflowapp.domain.modules.SurveyModule;
import com.equipo5.feelflowapp.dto.modules.TwelveStepsResponseAvgDto;
import com.equipo5.feelflowapp.service.dashboard.DashboardService;
import com.equipo5.feelflowapp.service.module.ModuleService;
import com.equipo5.feelflowapp.service.module.twelveSteps.TwelveStepsService;
import com.equipo5.feelflowapp.service.summary.twelvesteps.SummaryTwelveStepsService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class SummaryTwelveStepsServiceImpl implements SummaryTwelveStepsService {

    private final DashboardService dashboardService;
    private final ModuleService moduleService;
    private final TwelveStepsService twelveStepsService;

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
                    return dashboardService.getTwelveStepsResponseAvgDto(surveyModule.get().getSurveys());
                }
            }
        }
        return List.of();
    }

}
