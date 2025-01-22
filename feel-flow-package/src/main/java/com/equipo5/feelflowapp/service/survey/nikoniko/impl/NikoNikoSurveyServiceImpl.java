package com.equipo5.feelflowapp.service.survey.nikoniko.impl;


import com.equipo5.feelflowapp.domain.enumerations.modules.ActivityState;
import com.equipo5.feelflowapp.domain.enumerations.modules.ModuleNames;
import com.equipo5.feelflowapp.domain.enumerations.modules.SurveyStateEnum;
import com.equipo5.feelflowapp.domain.modules.Activity;
import com.equipo5.feelflowapp.domain.modules.ActivityNikoNiko;
import com.equipo5.feelflowapp.domain.modules.Survey;
import com.equipo5.feelflowapp.domain.modules.SurveyModule;
import com.equipo5.feelflowapp.domain.modules.nikoniko.NikoNikoModule;
import com.equipo5.feelflowapp.domain.users.RegularUser;
import com.equipo5.feelflowapp.dto.modules.ActivityDto;
import com.equipo5.feelflowapp.dto.modules.ActivityNikoNikoDto;
import com.equipo5.feelflowapp.dto.modules.SurveyAvailableNikoNikoReponseDto;
import com.equipo5.feelflowapp.dto.modules.SurveyNikoNikoResponseDto;
import com.equipo5.feelflowapp.dto.response.ResponseDto;
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

import java.time.LocalDate;
import java.time.LocalDateTime;
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
                ActivityNikoNiko activity1 = (ActivityNikoNiko) nikoNikoSurvey.get().getActivities().get(0);
                ActivityNikoNiko activity2 = (ActivityNikoNiko) nikoNikoSurvey.get().getActivities().get(1);

                NikoNikoModule nikoNikoModule = (NikoNikoModule) nikoNikoSurvey.get().getSurveyModule();
                long idSurvey = nikoNikoSurvey.get().getId();

                ActivityNikoNikoDto activityDto;
                int numberOfActivity;
                if( isTimeOfActivityOne( nikoNikoModule.getTimeToToResponseStartDay().toLocalTime(), nikoNikoModule.getTimeToToResponseEndDay().toLocalTime() ) ){
                    activityDto = super.activityMapper.activityToActivityNikoDto(activity1);
                    numberOfActivity = 1;
                }else if ( isTimeOfActivityTwo( nikoNikoModule.getTimeToToResponseEndDay().toLocalTime() ) ){
                    activityDto = super.activityMapper.activityToActivityNikoDto(activity2);
                    numberOfActivity = 2;
                }else{
                    return null;
                }
                return new SurveyAvailableNikoNikoReponseDto(idSurvey,numberOfActivity,activityDto);
            }

        }

        return null;
    }

    @Override
    public void completeSurvey(SurveyAvailableNikoNikoReponseDto surveyResponse) {

        //Buscar el survey, si existe y esta activa entonces completar la actividad.
        Optional<Survey> survey = super.surveyRepository.findById(surveyResponse.idSurvey());

        if(survey.isPresent() && SurveyStateEnum.ACTIVE.equals( survey.get().getSurveyStateEnum() ) ) {
            Optional<ActivityNikoNiko> activity = survey.get().getActivities()
                    .stream()
                    .filter(activityAvailable ->  activityAvailable.getQuestion().equals( surveyResponse.activityAvailable().question() ))
                    .map(activityFounded -> (ActivityNikoNiko) activityFounded)
                    .findFirst();

            if(activity.isPresent()){

                activity.get().setAnswer(surveyResponse.activityAvailable().answer());
                activity.get().setDescriptionFeeling( surveyResponse.activityAvailable().descriptionFeeling() );
                activity.get().setActivityState( ActivityState.FINISHED );
                super.surveyRepository.save( survey.get() );

            }

        }
    }

    private boolean isTimeOfActivityOne(LocalTime timeToResponseStartDay, LocalTime timeToResponseEndDay){
        return LocalTime.now().isAfter(timeToResponseStartDay) && LocalTime.now().isBefore(timeToResponseEndDay);
    }

    private boolean isTimeOfActivityTwo(LocalTime timeToResponseEndDay){
        return LocalTime.now().isAfter( timeToResponseEndDay ) &&
                LocalDateTime.of(LocalDate.now(),
                                LocalTime.of(timeToResponseEndDay.getHour(),timeToResponseEndDay.getMinute(),timeToResponseEndDay.getSecond())
                        ).isBefore(
                                LocalDateTime.of(LocalDate.now().plusDays(1),
                                        LocalTime.of(0,0,0))
                                );
    }

    private Optional<Survey> getSurveyNikoNikoAvailableByRegularUser(RegularUser regularUser){
        return super.surveyRepository
                .getAllByRegularUserAndSurveyStateEnum(regularUser, SurveyStateEnum.ACTIVE)
                .stream()
                .filter(survey -> ModuleNames.NIKO_NIKO.toString().equals( survey.getSurveyModule().getName() )
                ).findFirst();
    }


}
