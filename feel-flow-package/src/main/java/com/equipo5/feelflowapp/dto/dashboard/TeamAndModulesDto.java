package com.equipo5.feelflowapp.dto.dashboard;

import com.equipo5.feelflowapp.dto.modules.ModuleSurveyDto;
import com.equipo5.feelflowapp.dto.team.TeamDTO;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

@Schema(
        name = "TeamAndModulesDto",
        description = "Schema to hold team and its modules information"
)
public record TeamAndModulesDto(
        TeamDTO teamDTO,
        List<ModuleSurveyDto> moduleSurveyDtos
) {}
