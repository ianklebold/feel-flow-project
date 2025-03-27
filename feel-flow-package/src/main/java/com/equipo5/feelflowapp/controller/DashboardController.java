package com.equipo5.feelflowapp.controller;

import com.equipo5.feelflowapp.domain.enumerations.modules.ModuleNames;
import com.equipo5.feelflowapp.dto.dashboard.TeamAndModulesDto;
import com.equipo5.feelflowapp.dto.images.ImagesDto;
import com.equipo5.feelflowapp.dto.modules.ModuleDto;
import com.equipo5.feelflowapp.dto.modules.TwelveStepsResponseAvgDto;
import com.equipo5.feelflowapp.service.dashboard.DashboardService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@Tag(
        name = "Dashboard REST APIs",
        description = "REST APIs in Project to GET Data"
)
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping(path = DashboardController.PATH,produces = {MediaType.APPLICATION_JSON_VALUE})
public class DashboardController {
    public  static final String PATH = "/api/v1/dashboard";

    private final DashboardService dashboardService;

    @Operation(
            summary = "Get averaged data of 12 steps surveys",
            description = "REST API to get data 12 steps surveys"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Request Success"
            )
    })
    @GetMapping("/twelve_steps_avg")
    @SecurityRequirement(name = "Bearer Authentication")
    public List<TwelveStepsResponseAvgDto> getTwelveStepsSurveysAveragedData(
            @RequestParam(name = "idModuleTwelveSteps", required = false) Long idModuleTwelveSteps
            ){
        return dashboardService.getTwelveStepsSurveysAveragedData(idModuleTwelveSteps);
    }

    @Operation(
            summary = "Get averaged data of 12 steps surveys (Only for admin)",
            description = "REST API to get data 12 steps surveys (Only for admin)"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Request Success"
            )
    })
    @GetMapping("/twelve_steps_avg_admin")
    @SecurityRequirement(name = "Bearer Authentication")
    public List<TwelveStepsResponseAvgDto> getTwelveStepsSurveysAveragedData(
            @RequestParam(name = "idTeam", required = false) UUID idTeam,
            @RequestParam(name = "idModuleTwelveSteps", required = false) Long idModuleTwelveSteps
    ){
        return dashboardService.getTwelveStepsSurveysAveragedData(idModuleTwelveSteps, idTeam);
    }

    @Operation(
            summary = "Get data of teams and modules for 12 steps",
            description = "REST API to get data 12 steps surveys. If the current user is admin the response will be" +
                    "a list of modules and its team, otherwise if the current user is tl the response will be a list" +
                    "of modules an one team"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Request Success"
            )
    })
    @GetMapping("/teams-and-modules")
    @SecurityRequirement(name = "Bearer Authentication")
    public List<TeamAndModulesDto> getTeamsAndModulesData(
            @RequestParam(name = "nameModule") ModuleNames nameModule,
            @RequestParam(name = "isAdmin", defaultValue = "false") Boolean isAdmin
            ){
        return dashboardService.getTeamsAndModulesData(isAdmin,nameModule);
    }
}