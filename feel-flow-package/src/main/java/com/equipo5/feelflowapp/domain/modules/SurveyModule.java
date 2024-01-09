package com.equipo5.feelflowapp.domain.modules;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrimaryKeyJoinColumn;
import lombok.*;

import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
@Entity
@NoArgsConstructor
@PrimaryKeyJoinColumn(name = "surveymodule_module")
public class SurveyModule extends Module{

    @OneToMany(cascade = CascadeType.ALL)
    private List<Survey> surveys = new ArrayList<>();

    @Builder
    public SurveyModule(List<Survey> surveys) {
        super();
        this.surveys = surveys;
    }


}
