package com.equipo5.feelflowapp.service.survey.twelvesteps.impl;

import com.equipo5.feelflowapp.domain.enumerations.modules.ModuleNames;
import com.equipo5.feelflowapp.domain.enumerations.modules.SurveyStateEnum;
import com.equipo5.feelflowapp.domain.modules.Survey;
import com.equipo5.feelflowapp.domain.modules.SurveyModule;
import com.equipo5.feelflowapp.domain.users.RegularUser;
import com.equipo5.feelflowapp.dto.modules.SurveyResponseDto;
import com.equipo5.feelflowapp.exception.notfound.NotFoundException;
import com.equipo5.feelflowapp.mappers.modules.ActivityMapper;
import com.equipo5.feelflowapp.mappers.modules.SurveyMapper;
import com.equipo5.feelflowapp.repository.activity.ActivityRepository;
import com.equipo5.feelflowapp.repository.survey.SurveyRepository;
import com.equipo5.feelflowapp.repository.users.UserRepository;
import com.equipo5.feelflowapp.service.activity.ActivityService;
import com.equipo5.feelflowapp.service.survey.impl.SurveyServiceImpl;
import com.equipo5.feelflowapp.service.survey.twelvesteps.TwelveStepsSurveyService;
import com.equipo5.feelflowapp.service.users.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service("TwelveStepsSurveyService")
public class TwelveStepsSurveyServiceImpl extends SurveyServiceImpl implements TwelveStepsSurveyService {

    private final ActivityService activityService;

    private final ActivityMapper activityMapper;


    @Autowired
    public TwelveStepsSurveyServiceImpl(SurveyRepository surveyRepository, UserRepository userRepository, UserService userService, SurveyMapper surveyMapper, ActivityService activityService, ActivityMapper activityMapper) {
        super(surveyRepository, userRepository, userService, surveyMapper);
        this.activityService = activityService;
        this.activityMapper = activityMapper;
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


    @Override
    public void completeSurvey(SurveyResponseDto surveyResponse) {
        var twelveSteps = super.getSurveyActiveByModuleName(ModuleNames.TWELVE_STEPS);

        if (twelveSteps.isEmpty()){
            throw new IllegalStateException("No se tienes encuestas activas para el modulo 12 pasos hacia la felicidad");
        }

        Survey survey = super.getSurveyById(twelveSteps.get().idSurvey());

        var activitiesCompleted = activityMapper.getActivitiesFromDtoList(surveyResponse.activities());
        var activities = activityService.refreshActivities(survey,activitiesCompleted);
        survey.setActivities(activities);

        if (surveyResponse.surveyState().toString().equals(SurveyStateEnum.ACTIVE.toString())){
            //Encuesta no completada no pasa a finalizada
            survey.setSurveyStateEnum(SurveyStateEnum.ACTIVE);
        }else if (surveyResponse.surveyState().toString().equals(SurveyStateEnum.FINISHED.toString())){
            //Encuesta completada pasa a finalizada
            survey.setSurveyStateEnum(SurveyStateEnum.FINISHED);
            survey.setCloseDate(LocalDate.now());
            //Calcular usando algun metodo
        }else {
            throw new IllegalStateException("La encuesta se encuentra cerrada");
        }
        surveyRepository.save(survey);
    }




}
