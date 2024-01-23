package com.equipo5.feelflowapp.domain.modules.twelvesteps;

import com.equipo5.feelflowapp.domain.modules.Survey;
import com.equipo5.feelflowapp.domain.modules.SurveyModule;
import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@PrimaryKeyJoinColumn(name = "twelvesteps_module")
public class TwelveStepsModule extends SurveyModule {

    @Builder
    public TwelveStepsModule(List<Survey> surveys) {
        super(surveys);
    }

    public TwelveStepsModule() {
        super();
    }
}
