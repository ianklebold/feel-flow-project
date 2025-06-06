package com.equipo5.feelflowapp.service.point.impl;

import com.equipo5.feelflowapp.constants.module.nikoniko.ResponseConstantsNikoNiko;
import com.equipo5.feelflowapp.constants.module.twelvesteps.response.ResponseQuestionsConstants;
import com.equipo5.feelflowapp.domain.enumerations.modules.ActivityState;
import com.equipo5.feelflowapp.domain.modules.Activity;
import com.equipo5.feelflowapp.domain.modules.Survey;
import com.equipo5.feelflowapp.domain.users.RegularUser;
import com.equipo5.feelflowapp.service.point.PointService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class PointServiceImpl implements PointService {

    @Override
    public double getPointsByNikoNikoSurvey(Survey survey) {
        Activity activity1 = survey.getActivities().get(0);
        if(survey.getActivities().size() == 2){
            Activity activity2 = survey.getActivities().get(1);

            double point1 = ( isActivityCompleted( activity1 ))? getPointByNikoNikoSurvey(activity1): 0.5d;
            double point2 = ( isActivityCompleted( activity2 ))? getPointByNikoNikoSurvey(activity2): 0.5d;
            return ( point1 + point2 );

        }else{
            double point1 = ( isActivityCompleted( activity1 ))? getPointByNikoNikoSurvey(activity1): 0d;
            double point2 = 0d;
            return ( point1 + point2 );
        }
    }

    @Override
    public double getPointsByTwelveStepsSurvey(Survey survey) {

        double points = 0d;

        points =  survey.getActivities()
                .stream()
                .map(this::getPointBy12TwelveStepsSurvey)
                .reduce(0d, Double::sum);


       // return points / 12;

        return points;
    }

    @Override
    public double getTotalOfPointsPossibleNikoNiko(List<Survey> surveys, long sizeTeam){

        long totalOfActivities = surveys.size();

        return  totalOfActivities * 2;

    }

    @Override
    public double getTotalOfPointsPossibleNikoNiko(List<Survey> surveys, RegularUser regularUser){

        int totalOfActivities = surveys.stream()
                .filter( survey -> survey.getRegularUser().getUuid().equals( regularUser.getUuid()) )
                .toList()
                .size();

        return totalOfActivities * 2;

    }

    @Override
    public double getTotalOfPointsPossibleTwelveSteps(List<Survey> surveys, long sizeTeam) {

        long totalOfActivities =  surveys.size() * 12L;
        return  ( totalOfActivities * 5d );
    }

    @Override
    public double getTotalOfPointsPossibleTwelveSteps(List<Survey> surveys) {

        long totalOfActivities = 12L;
        return  ( totalOfActivities * 5d ) ;
    }

    private boolean isActivityCompleted(Activity activity){
        return ActivityState.FINISHED.equals(activity.getActivityState());
    }

    private double getPointByNikoNikoSurvey(Activity activity){

        if (!activity.getAnswer().isEmpty() || ActivityState.FINISHED.equals(activity.getActivityState()) ) {
            return switch (activity.getAnswer()) {
                case ResponseConstantsNikoNiko.ANSWERS_1_POOL_NIKO_NIKO -> 1.0d;
                case ResponseConstantsNikoNiko.ANSWERS_2_POOL_NIKO_NIKO -> 0.75d;
                case ResponseConstantsNikoNiko.ANSWERS_4_POOL_NIKO_NIKO -> 0.25d;
                case ResponseConstantsNikoNiko.ANSWERS_5_POOL_NIKO_NIKO -> 0d;
                default -> 0.5d;
            };
        }
        return 0d;
    }

    private double getPointBy12TwelveStepsSurvey(Activity activity){

        if (activity.getAnswer() != null && !activity.getAnswer().isEmpty()) {
            return switch (activity.getAnswer().substring(0,2)) {
                case ResponseQuestionsConstants.RESPONSE_OPTION_ONE -> 5d;
                case ResponseQuestionsConstants.RESPONSE_OPTION_TWO -> 4d;
                case ResponseQuestionsConstants.RESPONSE_OPTION_THREE -> 3d;
                case ResponseQuestionsConstants.RESPONSE_OPTION_FOUR -> 2d;
                case ResponseQuestionsConstants.RESPONSE_OPTION_FIVE -> 1d;
                default -> 0d;
            };
        }
        return 0d;
    }

}
