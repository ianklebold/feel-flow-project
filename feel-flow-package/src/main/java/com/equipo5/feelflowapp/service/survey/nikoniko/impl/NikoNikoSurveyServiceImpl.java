package com.equipo5.feelflowapp.service.survey.nikoniko.impl;


import com.equipo5.feelflowapp.domain.enumerations.modules.ModuleNames;
import com.equipo5.feelflowapp.domain.enumerations.modules.SurveyStateEnum;
import com.equipo5.feelflowapp.domain.modules.Activity;
import com.equipo5.feelflowapp.domain.modules.Survey;
import com.equipo5.feelflowapp.domain.modules.SurveyModule;
import com.equipo5.feelflowapp.domain.modules.nikoniko.NikoNikoModule;
import com.equipo5.feelflowapp.domain.users.RegularUser;
import com.equipo5.feelflowapp.dto.modules.ActivityDto;
import com.equipo5.feelflowapp.dto.modules.SurveyAvailableNikoNikoReponseDto;
import com.equipo5.feelflowapp.mappers.modules.ActivityMapper;
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

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Service("NikoNikoSurveyServiceImpl")
public class NikoNikoSurveyServiceImpl extends SurveyServiceImpl implements NikoNikoSurveyService {
    public NikoNikoSurveyServiceImpl(SurveyRepository surveyRepository, UserRepository userRepository, RegularUserRepository regularUserRepository, TeamRepository teamRepository, ModuleRepository moduleRepository, UserService userService, SurveyMapper surveyMapper, ModuleService moduleService,
                                     ActivityService activityService, ActivityMapper activityMapper) {
        super(surveyRepository, userRepository, regularUserRepository, teamRepository, moduleRepository, userService, surveyMapper, moduleService, activityMapper);
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

    @Override
    public SurveyAvailableNikoNikoReponseDto getSurveyAvailable() {
        String currentUserUsername =  super.userService.getUsernameByCurrentUser();
        Optional<RegularUser> regularUser = super.regularUserRepository.findByUsername( currentUserUsername );

        if (regularUser.isPresent()) {

            Optional<Survey> nikoNikoSurvey = getSurveyNikoNikoAvailableByRegularUser(regularUser.get());

            if(nikoNikoSurvey.isPresent() && nikoNikoSurvey.get().getActivities().size()  == 2){
                Activity activity1 = nikoNikoSurvey.get().getActivities().get(0);
                Activity activity2 = nikoNikoSurvey.get().getActivities().get(1);

                NikoNikoModule nikoNikoModule = (NikoNikoModule) nikoNikoSurvey.get().getSurveyModule();
                long idSurvey = nikoNikoSurvey.get().getId();

                ActivityDto activityDto;
                if(isTimeOfActivityOne(nikoNikoModule.getTimeToToResponseStartDay(), nikoNikoModule.getTimeToToResponseEndDay()) ){
                    activityDto = super.activityMapper.activityToActivityDto(activity1);
                }else if ( isTimeOfActivityTwo( nikoNikoModule.getTimeToToResponseEndDay() ) ){
                    activityDto = super.activityMapper.activityToActivityDto(activity2);
                }else{
                    return null;
                }
                return new SurveyAvailableNikoNikoReponseDto(idSurvey, activityDto);
            }

        }

        return null;
    }

    private boolean isTimeOfActivityOne(LocalTime timeToResponseStartDay, LocalTime timeToResponseEndDay){
        return timeToResponseStartDay.isBefore( LocalTime.now() ) && timeToResponseEndDay.isAfter( LocalTime.now() );
    }

    private boolean isTimeOfActivityTwo(LocalTime timeToResponseEndDay){
        return timeToResponseEndDay.isBefore( LocalTime.now() );
    }

    private Optional<Survey> getSurveyNikoNikoAvailableByRegularUser(RegularUser regularUser){
        return super.surveyRepository
                .getAllByRegularUserAndSurveyStateEnum(regularUser, SurveyStateEnum.ACTIVE)
                .stream()
                .filter(survey -> ModuleNames.NIKO_NIKO.toString().equals( survey.getSurveyModule().getName() )
                ).findFirst();
    }


}
