package com.equipo5.feelflowapp.domain.modules;

import com.equipo5.feelflowapp.domain.Team;
import com.equipo5.feelflowapp.domain.enumerations.modules.ModuleState;
import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@Entity
@PrimaryKeyJoinColumn(name = "surveymodule_module")
public class SurveyModule extends Module {

    public SurveyModule(UUID uuid, String name, ModuleState moduleState, Team team, List<PendingModule> pendingModules, LocalDateTime dateOfInit, LocalDateTime dateOfEnd) {
        super(uuid, name, moduleState, team, pendingModules, dateOfInit, dateOfEnd);
    }
}
