package com.equipo5.feelflowapp.domain.survies;

import com.equipo5.feelflowapp.domain.modules.TopicTwelveSteps;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrimaryKeyJoinColumn;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@PrimaryKeyJoinColumn(name = "twelvesteps_survey")
public class TwelveStepsSurvey extends Survey{

    @ManyToOne
    private TopicTwelveSteps surveyTopic;
}
