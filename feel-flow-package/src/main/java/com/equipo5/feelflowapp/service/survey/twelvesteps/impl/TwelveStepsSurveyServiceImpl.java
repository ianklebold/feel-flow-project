package com.equipo5.feelflowapp.service.survey.twelvesteps.impl;

import com.equipo5.feelflowapp.domain.enumerations.modules.ModuleNames;
import com.equipo5.feelflowapp.domain.enumerations.modules.SurveyStateEnum;
import com.equipo5.feelflowapp.domain.modules.Survey;
import com.equipo5.feelflowapp.domain.modules.SurveyModule;
import com.equipo5.feelflowapp.domain.modules.twelvesteps.TwelveStepsModule;
import com.equipo5.feelflowapp.domain.users.RegularUser;
import com.equipo5.feelflowapp.dto.modules.SurveyTwelveStepsResponseDto;
import com.equipo5.feelflowapp.exception.badrequest.survey.SurveyException;
import com.equipo5.feelflowapp.mappers.modules.ActivityMapper;
import com.equipo5.feelflowapp.mappers.modules.SurveyMapper;
import com.equipo5.feelflowapp.repository.survey.SurveyRepository;
import com.equipo5.feelflowapp.repository.users.UserRepository;
import com.equipo5.feelflowapp.service.activity.ActivityService;
import com.equipo5.feelflowapp.service.report.ReportService;
import com.equipo5.feelflowapp.service.survey.SurveyService;
import com.equipo5.feelflowapp.service.survey.impl.SurveyServiceImpl;
import com.equipo5.feelflowapp.service.survey.twelvesteps.TwelveStepsSurveyService;
import com.equipo5.feelflowapp.service.users.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Getter
@Service("TwelveStepsSurveyService")
public class TwelveStepsSurveyServiceImpl extends SurveyServiceImpl implements TwelveStepsSurveyService {

    private final ActivityService activityService;

    private final ActivityMapper activityMapper;

    private final ReportService reportService;


    @Autowired
    public TwelveStepsSurveyServiceImpl(SurveyRepository surveyRepository, UserRepository userRepository, UserService userService, SurveyMapper surveyMapper, ActivityService activityService, ActivityMapper activityMapper, ReportService reportService) {
        super(surveyRepository, userRepository, userService, surveyMapper);
        this.activityService = activityService;
        this.activityMapper = activityMapper;
        this.reportService = reportService;
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
    public void completeSurvey(SurveyTwelveStepsResponseDto surveyResponse) throws JsonProcessingException {
        var twelveSteps = super.getSurveyActiveByModuleName(ModuleNames.TWELVE_STEPS);

        if (twelveSteps.isEmpty()){
            throw new SurveyException("No se tienes encuestas activas para el modulo 12 pasos hacia la felicidad");
        }

        Survey survey = super.getSurveyById(twelveSteps.get().idSurvey());

        var activitiesCompleted = activityMapper.getActivitiesFromDtoList(surveyResponse.activities());
        var activities = activityService.refreshActivities(activitiesCompleted);
        survey.setActivities(activities);

        if (surveyResponse.surveyState().toString().equals(SurveyStateEnum.ACTIVE.toString())){
            //Encuesta no completada no pasa a finalizada
            survey.setSurveyStateEnum(SurveyStateEnum.ACTIVE);
        }else if (surveyResponse.surveyState().toString().equals(SurveyStateEnum.FINISHED.toString())){
            //Encuesta completada pasa a finalizada
            survey.setSurveyStateEnum(SurveyStateEnum.FINISHED);
            survey.setCloseDate(LocalDate.now());
            //Calcular usando algun metodo
            var report = reportService.createReportToTwelveSteps(survey);
            survey.setReport(report);
        }else {
            throw new SurveyException("La encuesta se encuentra cerrada");
        }
        surveyRepository.save(survey);
    }

    @Override
    public void forceToCloseSurvey(List<Survey> surveyList) {
        surveyList.forEach(survey -> {
            if (!survey.getSurveyStateEnum().toString().equals(SurveyStateEnum.FINISHED.toString())) {
                survey.setActivities(activityService.forceTocloseActivities(survey.getActivities()));
                survey.setCloseDate(LocalDate.now());
            }
            survey.setSurveyStateEnum(SurveyStateEnum.CLOSED);
        }
        );
        surveyRepository.saveAll(surveyList);
    }


}
