package com.equipo5.feelflowapp.dto.module;

import com.equipo5.feelflowapp.domain.enumerations.modules.ModuleState;
import com.equipo5.feelflowapp.dto.team.TeamDTO;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ModuleDTO {
    private UUID uuid;
    private String name;
    private ModuleState moduleState;
    private TeamDTO teamDTO;
    private List<PendingModuleDTO> pendingModuleDTO;
    private LocalDateTime dateOfInit;
    private LocalDateTime dateOfEnd;
}
