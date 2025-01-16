package com.equipo5.feelflowapp.jobs.module.surveys.impl;

import com.equipo5.feelflowapp.domain.Team;
import com.equipo5.feelflowapp.domain.enumerations.modules.ActivityState;
import com.equipo5.feelflowapp.domain.enumerations.modules.ModuleState;
import com.equipo5.feelflowapp.domain.modules.SurveyModule;
import com.equipo5.feelflowapp.domain.modules.nikoniko.NikoNikoModule;
import com.equipo5.feelflowapp.jobs.module.surveys.ClosingSurveisScheduledTask;
import com.equipo5.feelflowapp.jobs.module.surveys.SurveyScheduledTask;
import com.equipo5.feelflowapp.repository.activity.ActivityRepository;
import com.equipo5.feelflowapp.repository.module.ModuleNikoNikoRepository;
import com.equipo5.feelflowapp.repository.survey.SurveyRepository;
import com.equipo5.feelflowapp.repository.team.TeamRepository;
import com.equipo5.feelflowapp.service.survey.nikoniko.NikoNikoSurveyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
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

    public NikoNikoSurveyScheduledTaskImpl(TeamRepository teamRepository, NikoNikoSurveyService nikoNikoSurveyService, ModuleNikoNikoRepository nikoNikoRepository, SurveyRepository surveyRepository, ActivityRepository activityRepository) {
        this.teamRepository = teamRepository;
        this.nikoNikoSurveyService = nikoNikoSurveyService;
        this.nikoNikoRepository = nikoNikoRepository;
        this.surveyRepository = surveyRepository;
        this.activityRepository = activityRepository;
    }

    @Override
    @Scheduled(cron = "0 0 0 * * ?")
    public void sendSurveys() {
        List<Team> teams = teamRepository.findAll();

        if (!teams.isEmpty()) {

            teams.forEach((
                            team -> {
                                var module = team.getModules().stream().filter(
                                        nikoNiko -> NIKO_NIKO.toString().equals(nikoNiko.getName()) && ModuleState.ACTIVE.equals(nikoNiko.getModuleState()
                                        )
                                )
                                        .map(  nikoNiko -> (NikoNikoModule) nikoNiko )
                                        .findFirst()
                                        .orElse(null);

                                if (module != null) {
                                    if( isModuleEnabled( module.getDateAndTimeToPublish() ) ){
                                        closeOldActivities(module);
                                        nikoNikoSurveyService.createSurveis(team.getRegularUsers(), module);
                                        nikoNikoRepository.save( module );
                                        log.info(String.format("Niko surveis created: For User of the team %s", team.getName() ));
                                    }else{
                                        log.error(" Date and time to publish is before to today - Module not enabled ");
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

    private boolean isModuleEnabled(LocalDateTime localDateTimeModule) {
        return localDateTimeModule.isAfter(LocalDateTime.now());
    }

    private void closeOldActivities(NikoNikoModule nikoNikoModule){
        nikoNikoModule
                .getSurveys()
                .forEach(survey -> {
                    survey.getActivities()
                            .forEach(
                                    activity -> {
                                        if (!ActivityState.FINISHED.toString().equals(activity.getActivityState().toString()) && activity.getAnswer() == null){
                                            activity.setActivityState(ActivityState.FINISHED);
                                            activityRepository.save(activity);
                                            nikoNikoRepository.save(nikoNikoModule);
                                        }
                                    }
                            );
                });
    }

}
