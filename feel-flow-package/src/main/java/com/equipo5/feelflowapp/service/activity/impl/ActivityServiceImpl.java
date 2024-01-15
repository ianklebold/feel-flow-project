package com.equipo5.feelflowapp.service.activity.impl;

import com.equipo5.feelflowapp.domain.enumerations.modules.ActivityState;
import com.equipo5.feelflowapp.domain.modules.Activity;
import com.equipo5.feelflowapp.repository.activity.ActivityRepository;
import com.equipo5.feelflowapp.service.activity.ActivityService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ActivityServiceImpl implements ActivityService {

    private final ActivityRepository activityRepository;

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
}
