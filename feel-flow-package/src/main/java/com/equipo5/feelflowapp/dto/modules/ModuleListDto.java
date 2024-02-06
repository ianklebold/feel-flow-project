package com.equipo5.feelflowapp.dto.modules;

import com.equipo5.feelflowapp.domain.enumerations.modules.ModuleState;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Schema(
        name = "ModuleListDto",
        description = "Schema to hold module information"
)
@AllArgsConstructor
@Builder
@Getter
@Setter
public class ModuleListDto{
        @Schema(description = "Id of module") Long id;
        @Schema(description = "Name of module", example = "NIKO NIKO") String name;
        @Schema(description = "Date of creation module", example = "2024-12-31") LocalDate creationDate;
        @Schema(description = "Date of close module", example = "2024-12-31") LocalDate closeDate;
        @Schema(description = "State of module", examples = {"ACTIVE","FINISHED"}) ModuleState moduleState;
        @Schema(description = "List of Surveys")List<SurveyListDto> surveyList;
        boolean isEnableToClose;
}
