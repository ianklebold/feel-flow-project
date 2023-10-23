package com.equipo5.feelflowapp.domain.survies;

import com.equipo5.feelflowapp.domain.modules.TopicTwelveSteps;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.PrimaryKeyJoinColumn;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@PrimaryKeyJoinColumn(name = "twelvesteps_survey")
public class TwelveStepsSurvey extends Survey{

    @ManyToMany
    private List<TopicTwelveSteps> surveyModules= new ArrayList<>();
}
