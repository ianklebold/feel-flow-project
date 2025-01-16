package com.equipo5.feelflowapp.service.survey.nikoniko.impl;


import com.equipo5.feelflowapp.domain.modules.SurveyModule;
import com.equipo5.feelflowapp.domain.users.RegularUser;
import com.equipo5.feelflowapp.mappers.modules.SurveyMapper;
import com.equipo5.feelflowapp.repository.module.ModuleRepository;
import com.equipo5.feelflowapp.repository.survey.SurveyRepository;
import com.equipo5.feelflowapp.repository.team.TeamRepository;
import com.equipo5.feelflowapp.repository.users.UserRepository;
import com.equipo5.feelflowapp.repository.users.regularuser.RegularUserRepository;
import com.equipo5.feelflowapp.service.activity.ActivityService;
import com.equipo5.feelflowapp.service.module.ModuleService;
import com.equipo5.feelflowapp.service.survey.impl.SurveyServiceImpl;
import com.equipo5.feelflowapp.service.survey.nikoniko.NikoNikoSurveyService;
import com.equipo5.feelflowapp.service.users.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("NikoNikoSurveyServiceImpl")
public class NikoNikoSurveyServiceImpl extends SurveyServiceImpl implements NikoNikoSurveyService {
    public NikoNikoSurveyServiceImpl(SurveyRepository surveyRepository, UserRepository userRepository, RegularUserRepository regularUserRepository, TeamRepository teamRepository, ModuleRepository moduleRepository, UserService userService, SurveyMapper surveyMapper, ModuleService moduleService, ActivityService activityService) {
        super(surveyRepository, userRepository, regularUserRepository, teamRepository, moduleRepository, userService, surveyMapper, moduleService);
        this.activityService = activityService;
    }

    private final ActivityService activityService;

    @Override
    @Transactional
    public void createSurveis(List<RegularUser> users, SurveyModule module) {
        super.createSurveis(users, module);
        module.getSurveys()
                .forEach(survey -> survey.setActivities( activityService.createActivityToNikoNiko() ));
    }


}
