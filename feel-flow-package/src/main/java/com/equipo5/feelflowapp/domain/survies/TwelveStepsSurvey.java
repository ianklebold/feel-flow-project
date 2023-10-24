package com.equipo5.feelflowapp.domain.survies;

import com.equipo5.feelflowapp.domain.modules.SurveyModule;
import com.equipo5.feelflowapp.domain.modules.TopicTwelveSteps;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrimaryKeyJoinColumn;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@PrimaryKeyJoinColumn(name = "twelvesteps_survey")
public class TwelveStepsSurvey extends Survey{

    @ManyToMany
    private List<TopicTwelveSteps> surveyTopic = new ArrayList<>();

    @Builder
    public TwelveStepsSurvey(Long id, String question, List<SurveyModule> surveyModule, List<TopicTwelveSteps> surveyTopic) {
        super(id, question, surveyModule);
        this.surveyTopic = surveyTopic;
    }

    @Builder
    public TwelveStepsSurvey(List<TopicTwelveSteps> surveyTopic) {
        this.surveyTopic = surveyTopic;
    }

    public TwelveStepsSurvey() {

    }
}
