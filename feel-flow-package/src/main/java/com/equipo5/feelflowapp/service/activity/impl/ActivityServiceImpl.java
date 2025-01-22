package com.equipo5.feelflowapp.service.activity.impl;

import com.equipo5.feelflowapp.constants.module.nikoniko.QuestionsConstantsNikoNiko;
import com.equipo5.feelflowapp.constants.module.twelvesteps.QuestionsConstantsTwelveSteps;
import com.equipo5.feelflowapp.domain.enumerations.modules.ActivityState;
import com.equipo5.feelflowapp.domain.modules.Activity;
import com.equipo5.feelflowapp.domain.modules.ActivityNikoNiko;
import com.equipo5.feelflowapp.domain.modules.Survey;
import com.equipo5.feelflowapp.mappers.modules.ActivityMapper;
import com.equipo5.feelflowapp.repository.activity.ActivityRepository;
import com.equipo5.feelflowapp.service.activity.ActivityService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ActivityServiceImpl implements ActivityService {

    private final ActivityRepository activityRepository;

    private final ActivityMapper activityMapper;


    @Override
    public List<Activity> createActivityToNikoNiko() {

        var activities = new ArrayList<Activity>();
        activities.add(
                ActivityNikoNiko
                        .builder()
                        .question(QuestionsConstantsNikoNiko.ANSWERS_1_POOL_NIKO_NIKO)
                        .activityState(ActivityState.ACTIVE)
                        .answer(null)
                        .build()
        );

        activities.add(
                ActivityNikoNiko
                        .builder()
                        .question(QuestionsConstantsNikoNiko.ANSWERS_2_POOL_NIKO_NIKO)
                        .activityState(ActivityState.ACTIVE)
                        .answer(null)
                        .build()
        );

        activityRepository.saveAll(activities);
        return activities;
    }

    @Override
    public List<Activity> createActivityToTwelveSteps() {

        var activities = new ArrayList<Activity>();

        for (int i = 0; i < 12; i++) {
            activities.add(
                    Activity
                            .builder()
                            .question(QuestionsConstantsTwelveSteps.QUESTIONS_POOL_CLASSIC_TWELVE_STEPS.get(i))
                            .activityState(ActivityState.ACTIVE)
                            .build()
            );
        }

        activityRepository.saveAll(activities);
        return activities;
    }

    @Override
    public List<Activity> refreshActivities(Survey survey,List<Activity> activitiesCompleted) {
        activitiesCompleted.forEach(Activity::openActivity);
        activitiesCompleted.forEach(Activity::closeActivity);

        return activityRepository.saveAll(activitiesCompleted);
    }


}
