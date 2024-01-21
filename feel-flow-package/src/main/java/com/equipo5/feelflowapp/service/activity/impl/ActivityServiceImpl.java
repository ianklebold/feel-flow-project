package com.equipo5.feelflowapp.service.activity.impl;

import com.equipo5.feelflowapp.domain.enumerations.modules.ActivityState;
import com.equipo5.feelflowapp.domain.modules.Activity;
import com.equipo5.feelflowapp.domain.modules.Survey;
import com.equipo5.feelflowapp.mappers.modules.ActivityMapper;
import com.equipo5.feelflowapp.repository.activity.ActivityRepository;
import com.equipo5.feelflowapp.service.activity.ActivityService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ActivityServiceImpl implements ActivityService {

    private final ActivityRepository activityRepository;

    private final ActivityMapper activityMapper;


    @Override
    public List<Activity> createActivityToTwelveSteps() {

        var activities = new ArrayList<Activity>();

        for (int i = 0; i < 13; i++) {
            activities.add(
                    Activity
                            .builder()
                            .question("Question" + UUID.randomUUID())
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

        return activityRepository.saveAll(survey.getActivities());
    }


}
