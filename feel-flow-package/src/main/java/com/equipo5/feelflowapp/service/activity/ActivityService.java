package com.equipo5.feelflowapp.service.activity;


import com.equipo5.feelflowapp.domain.modules.Activity;
import com.equipo5.feelflowapp.domain.modules.Survey;

import java.util.List;

public interface ActivityService {
    List<Activity> createActivityToTwelveSteps();

    List<Activity> refreshActivities(Survey survey,List<Activity> activitiesCompleted);

}
