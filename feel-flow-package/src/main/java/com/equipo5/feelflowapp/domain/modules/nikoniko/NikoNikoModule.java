package com.equipo5.feelflowapp.domain.modules.nikoniko;

import com.equipo5.feelflowapp.domain.modules.Survey;
import com.equipo5.feelflowapp.domain.modules.SurveyModule;
import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalTime;
import java.util.List;
@Entity
@Getter
@Setter
@PrimaryKeyJoinColumn(name = "niko_niko_module")
public class NikoNikoModule extends SurveyModule {

    private LocalTime timeToToResponseStartDay;

    private LocalTime timeToToResponseEndDay;


    @Builder
    public NikoNikoModule(List<Survey> surveys, LocalTime timeToToResponseStartDay, LocalTime timeToToResponseEndDay) {
        super(surveys);
        this.timeToToResponseStartDay = timeToToResponseStartDay;
        this.timeToToResponseEndDay = timeToToResponseEndDay;
    }

    @Builder
    public NikoNikoModule(List<Survey> surveys) {
        super(surveys);
    }

    @Builder
    public NikoNikoModule(LocalTime timeToToResponseStartDay, LocalTime timeToToResponseEndDay) {
        super();
    }

    @Builder
    public NikoNikoModule() {
        super();
    }
}

