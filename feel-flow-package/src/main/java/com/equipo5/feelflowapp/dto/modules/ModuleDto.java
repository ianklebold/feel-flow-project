package com.equipo5.feelflowapp.dto.modules;

import com.equipo5.feelflowapp.domain.enumerations.modules.ModuleState;
import com.equipo5.feelflowapp.dto.team.TeamDTO;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDate;

@Schema(
        name = "ModuleDto",
        description = "Schema to hold module information"
)
public record ModuleDto(
        @Schema(description = "Name of module", example = "NIKO NIKO") String name,
        @Schema(description = "Date of creation module", example = "2024-12-31") LocalDate creationDate,
        @Schema(description = "State of module", examples = {"ACTIVE","FINISHED"}) ModuleState moduleState,
        @Schema(description = "Team owner of the module") TeamDTO team
){}
