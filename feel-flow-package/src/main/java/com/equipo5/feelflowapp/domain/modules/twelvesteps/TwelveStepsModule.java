package com.equipo5.feelflowapp.domain.modules.twelvesteps;

import com.equipo5.feelflowapp.domain.modules.Survey;
import com.equipo5.feelflowapp.domain.modules.SurveyModule;
import jakarta.persistence.Entity;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
public class TwelveStepsModule extends SurveyModule {
    public TwelveStepsModule(List<Survey> surveys) {
        super(surveys);
    }

    public TwelveStepsModule() {
        super();
    }
}
