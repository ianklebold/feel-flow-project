package com.equipo5.feelflowapp.jobs.module.surveys.impl;

import com.equipo5.feelflowapp.domain.Team;
import com.equipo5.feelflowapp.domain.enumerations.modules.ActivityState;
import com.equipo5.feelflowapp.domain.enumerations.modules.ModuleState;
import com.equipo5.feelflowapp.domain.enumerations.modules.SurveyStateEnum;
import com.equipo5.feelflowapp.domain.modules.SurveyModule;
import com.equipo5.feelflowapp.domain.modules.nikoniko.NikoNikoModule;
import com.equipo5.feelflowapp.exception.badrequest.survey.SurveyException;
import com.equipo5.feelflowapp.jobs.module.surveys.ClosingSurveisScheduledTask;
import com.equipo5.feelflowapp.jobs.module.surveys.SurveyScheduledTask;
import com.equipo5.feelflowapp.repository.activity.ActivityRepository;
import com.equipo5.feelflowapp.repository.module.ModuleNikoNikoRepository;
import com.equipo5.feelflowapp.repository.survey.SurveyRepository;
import com.equipo5.feelflowapp.repository.team.TeamRepository;
import com.equipo5.feelflowapp.service.notification.NotificationService;
import com.equipo5.feelflowapp.service.survey.nikoniko.NikoNikoSurveyService;
import com.equipo5.feelflowapp.service.utils.dateservice.DateUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static com.equipo5.feelflowapp.domain.enumerations.modules.ModuleNames.NIKO_NIKO;

@Slf4j
@Component
public class NikoNikoSurveyScheduledTaskImpl implements SurveyScheduledTask{

    private final TeamRepository teamRepository;

    private final NikoNikoSurveyService nikoNikoSurveyService;

    private final ModuleNikoNikoRepository nikoNikoRepository;

    private final SurveyRepository surveyRepository;
    private final ActivityRepository activityRepository;
    private final NotificationService notificationService;

    public NikoNikoSurveyScheduledTaskImpl(TeamRepository teamRepository, NikoNikoSurveyService nikoNikoSurveyService, ModuleNikoNikoRepository nikoNikoRepository, SurveyRepository surveyRepository, ActivityRepository activityRepository, NotificationService notificationService) {
        this.teamRepository = teamRepository;
        this.nikoNikoSurveyService = nikoNikoSurveyService;
        this.nikoNikoRepository = nikoNikoRepository;
        this.surveyRepository = surveyRepository;
        this.activityRepository = activityRepository;
        this.notificationService = notificationService;
    }

    @Override
    @Scheduled(cron = "0 0 0 * * ?")
    public void sendSurveys() {
        List<Team> teams = teamRepository.findAll();

        if (!teams.isEmpty()) {

            teams.forEach((
                            team -> {
                                var module = team.getModules().stream().filter(
                                        nikoNiko -> NIKO_NIKO.toString().equals(nikoNiko.getName()) && ModuleState.ACTIVE.equals(nikoNiko.getModuleState())
                                )
                                        .map(  nikoNiko -> (NikoNikoModule) nikoNiko )
                                        .findFirst()
                                        .orElse(null);

                                if (module != null) {
                                    if( isModuleEnabled( module.getDateAndTimeToPublish(), module.getDateAndTimeToClose() ) ){
                                        closeOldActivities(module);
                                        checkIfNotExistsSurveysEnabled(module);
                                        nikoNikoSurveyService.createSurveis(team.getRegularUsers(), module);
                                        notificationService.sendNotificationSurveyAvailableNikoNiko( module );
                                        nikoNikoRepository.save( module );
                                        log.info(String.format("Niko surveis created: For User of the team %s", team.getName() ));
                                    }else if ( DateUtils.isBeforeToOtherDate( module.getDateAndTimeToClose(), null  )){
                                        log.error(" Module is not enabled - Closing Module ");
                                        this.closeModule( module );
                                    }
                                }else{
                                    log.error(" Module is null ");
                                }
                            }
                            )
                    );
        }else{
            log.error(" Teams is empty ");
        }

    }


    private void closeOldActivities(NikoNikoModule nikoNikoModule){
        nikoNikoModule
                .getSurveys()
                .forEach(survey -> {
                    survey.getActivities()
                            .forEach(
                                    activity -> {
                                        if (
                                                !ActivityState.FINISHED.toString().equals(activity.getActivityState().toString())
                                                && activity.getAnswer() == null
                                                && DateUtils.isEqualOrMoreThanOneDayAgo(nikoNikoModule.getDateAndTimeToPublish())
                                        ){
                                            activity.setActivityState(ActivityState.FINISHED);
                                            activityRepository.save(activity);
                                            nikoNikoRepository.save(nikoNikoModule);
                                        }
                                    }
                            );
                });
    }

    private void checkIfNotExistsSurveysEnabled(NikoNikoModule nikoNikoModule){
        boolean isAnySurveyActive = nikoNikoModule
                .getSurveys().stream()
                .anyMatch(survey -> SurveyStateEnum.ACTIVE.toString().equals(survey.getSurveyStateEnum().toString()));

        if (isAnySurveyActive){
            throw new SurveyException("Existe encuestas activas, no es posible crearlas hasta cerrarlas.");
        }

    }

    private void closeModule(NikoNikoModule nikoNikoModule){
        nikoNikoModule.setModuleState(ModuleState.FINISHED);
        nikoNikoRepository.save(nikoNikoModule);
    }

    private boolean isModuleEnabled(Timestamp dateAndTimeToPublish, Timestamp dateAndTimeToClose) {
        return DateUtils.isAfterToToday(dateAndTimeToPublish) || DateUtils.isEqualToToday(dateAndTimeToPublish)
                && DateUtils.isBeforeToToday(dateAndTimeToClose) || DateUtils.isEqualToToday(dateAndTimeToPublish);
    }

}
