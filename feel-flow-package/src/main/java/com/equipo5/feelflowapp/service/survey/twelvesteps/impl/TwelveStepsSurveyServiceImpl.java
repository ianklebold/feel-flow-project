package com.equipo5.feelflowapp.service.survey.twelvesteps.impl;

import com.equipo5.feelflowapp.domain.modules.SurveyModule;
import com.equipo5.feelflowapp.domain.users.RegularUser;
import com.equipo5.feelflowapp.repository.survey.SurveyRepository;
import com.equipo5.feelflowapp.service.activity.ActivityService;
import com.equipo5.feelflowapp.service.survey.impl.SurveyServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("TwelveStepsSurveyService")
@RequiredArgsConstructor
public class TwelveStepsSurveyServiceImpl extends SurveyServiceImpl{

    private final ActivityService activityService;

    private final SurveyRepository surveyRepository;

    @Override
    @Transactional
    public void createSurveis(List<RegularUser> users, SurveyModule module) {
        super.createSurveis(users,module);
        //Crear actividades
        module.getSurveys()
                .forEach(survey -> survey.setActivities( activityService.createActivityToTwelveSteps() ));

        surveyRepository.saveAll(module.getSurveys());
    }
}
