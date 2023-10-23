package com.equipo5.feelflowapp.domain.modules;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.PrimaryKeyJoinColumn;
import lombok.*;

import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
@Entity
@PrimaryKeyJoinColumn(name = "twelvesteps_module")
public class TwelveStepsModule extends SurveyModule{

    @ManyToMany
    private List<TopicTwelveSteps> topics = new ArrayList<>();
}
