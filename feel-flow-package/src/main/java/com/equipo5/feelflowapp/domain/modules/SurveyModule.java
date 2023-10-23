package com.equipo5.feelflowapp.domain.modules;

import com.equipo5.feelflowapp.domain.enumerations.modules.ModuleState;
import com.equipo5.feelflowapp.domain.survies.Survey;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrimaryKeyJoinColumn;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@PrimaryKeyJoinColumn(name = "surveymodule_module")
public class SurveyModule extends Module {

    @OneToMany(cascade = CascadeType.ALL,mappedBy = "surveyModule")
    private List<Survey> surveys = new ArrayList<>();

    @Builder
    public SurveyModule(UUID uuid, String name, ModuleState moduleState, List<PendingModule> pendingModules, LocalDateTime dateOfInit, LocalDateTime dateOfEnd, List<Survey> surveys) {
        super(uuid, name, moduleState, pendingModules, dateOfInit, dateOfEnd);
        this.surveys = surveys;
    }
}
