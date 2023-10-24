package com.equipo5.feelflowapp.domain.modules;

import com.equipo5.feelflowapp.domain.Team;
import com.equipo5.feelflowapp.domain.enumerations.modules.ModuleState;
import com.equipo5.feelflowapp.domain.survies.Survey;
import com.equipo5.feelflowapp.domain.survies.TwelveStepsSurvey;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@Getter
@Setter
@Entity
@NoArgsConstructor
@PrimaryKeyJoinColumn(name = "twelvesteps_module")
public class TwelveStepsModule extends SurveyModule{

    @OneToMany(cascade = CascadeType.ALL,mappedBy = "surveyModule")
    private List<TwelveStepsSurvey> surveys = new ArrayList<>();

    @Builder
    public TwelveStepsModule(UUID uuid, String name, ModuleState moduleState, Team team, List<PendingModule> pendingModules, LocalDateTime dateOfInit, LocalDateTime dateOfEnd, List<TwelveStepsSurvey> surveys) {
        super(uuid, name, moduleState, team, pendingModules, dateOfInit, dateOfEnd);
        this.surveys = surveys;
    }
}
