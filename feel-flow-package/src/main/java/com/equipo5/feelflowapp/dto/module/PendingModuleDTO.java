package com.equipo5.feelflowapp.dto.module;

import com.equipo5.feelflowapp.domain.answers.Answer;
import com.equipo5.feelflowapp.domain.enumerations.modules.ModuleState;
import com.equipo5.feelflowapp.dto.users.UserDTO;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Builder
public class PendingModuleDTO {
    private UUID uuid;
    private ModuleState moduleState;
    private List<Answer> answers;
    private UserDTO userDTO;
    private LocalDateTime dateOfInit;
    private LocalDateTime dateOfEnd;
}
