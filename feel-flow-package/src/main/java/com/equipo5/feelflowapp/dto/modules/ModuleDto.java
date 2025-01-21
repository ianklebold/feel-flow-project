package com.equipo5.feelflowapp.dto.modules;

import com.equipo5.feelflowapp.domain.enumerations.modules.ModuleNames;
import com.equipo5.feelflowapp.domain.enumerations.modules.ModuleState;
import com.equipo5.feelflowapp.dto.team.TeamDTO;
import io.swagger.v3.oas.annotations.media.Schema;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Schema(
        name = "ModuleDto",
        description = "Schema to hold module information"
)
public record ModuleDto(
        @Schema(description = "Name of module", example = "NIKO NIKO") String name,
        @Schema(description = "Date of creation module", example = "2024-12-31") LocalDate creationDate,
        @Schema(description = "State of module", examples = {"ACTIVE","FINISHED"}) ModuleState moduleState,
        @Schema(description = "Date and time of the survey for to be available", example = "999999999-12-31T23:59:59" ) Timestamp dateAndTimeToPublish,
        @Schema(description = "Date and time of the survey for to be closed", example = "999999999-12-31T23:59:59" ) Timestamp dateAndTimeToClose,
        @Schema(description = "Team owner of the module") TeamDTO team
){

        public boolean filterTwelveStepsModules(ModuleNames moduleNames){
            return this.name.equals(moduleNames.toString());
        }
}
