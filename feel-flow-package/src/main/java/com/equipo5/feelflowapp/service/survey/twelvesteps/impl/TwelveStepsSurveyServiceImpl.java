package com.equipo5.feelflowapp.service.survey.twelvesteps.impl;

import com.equipo5.feelflowapp.domain.modules.SurveyModule;
import com.equipo5.feelflowapp.domain.users.RegularUser;
import com.equipo5.feelflowapp.mappers.modules.SurveyMapper;
import com.equipo5.feelflowapp.repository.survey.SurveyRepository;
import com.equipo5.feelflowapp.repository.users.UserRepository;
import com.equipo5.feelflowapp.service.activity.ActivityService;
import com.equipo5.feelflowapp.service.survey.impl.SurveyServiceImpl;
import com.equipo5.feelflowapp.service.users.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("TwelveStepsSurveyService")
public class TwelveStepsSurveyServiceImpl extends SurveyServiceImpl{

    private final ActivityService activityService;

    @Autowired
    public TwelveStepsSurveyServiceImpl(SurveyRepository surveyRepository, UserRepository userRepository, UserService userService, SurveyMapper surveyMapper, ActivityService activityService) {
        super(surveyRepository, userRepository, userService, surveyMapper);
        this.activityService = activityService;
    }

    @Override
    @Transactional
    public void createSurveis(List<RegularUser> users, SurveyModule module) {
        super.createSurveis(users,module);
        //Crear actividades
        module.getSurveys()
                .forEach(survey -> survey.setActivities( activityService.createActivityToTwelveSteps() ));

        super.surveyRepository.saveAll(module.getSurveys());
    }
}
