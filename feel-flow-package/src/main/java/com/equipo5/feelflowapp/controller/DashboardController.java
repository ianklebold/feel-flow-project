package com.equipo5.feelflowapp.controller;

import com.equipo5.feelflowapp.domain.enumerations.modules.ModuleNames;
import com.equipo5.feelflowapp.dto.dashboard.TeamAndModulesDto;
import com.equipo5.feelflowapp.dto.dashboard.nikoniko.NikoNikoSummaryData;
import com.equipo5.feelflowapp.dto.modules.ModuleAndUsersDto;
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
    public List<TwelveStepsResponseAvgDto> getTwelveStepsSurveysAveragedData(){
        return dashboardService.getTwelveStepsSurveysAveragedData();
    }

    @Operation(
            summary = "Get summary averaged data of 12 steps surveys",
            description = "REST API to get data 12 steps surveys"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Request Success"
            )
    })
    @GetMapping("/twelve_steps_summary")
    @SecurityRequirement(name = "Bearer Authentication")
    public List<TwelveStepsResponseAvgDto> getTwelveStepsSurveysSummaryData(
            @RequestParam(name = "idTeam", required = false) Long idModule,
            @RequestParam(name = "idModuleTwelveSteps", required = false) UUID idRegularUser
    ){
        return dashboardService.getTwelveStepsSurveysSummaryData(idModule, idRegularUser);
    }

    @Operation(
            summary = "Get data of teams and modules for 12 steps",
            description = "REST API to get data of the surveys. If the current user is admin the response will be" +
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

    @Operation(
            summary = "Get data of module and users",
            description = "REST API to get data of module and users."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Request Success"
            )
    })
    @GetMapping("/modules-and-users")
    @SecurityRequirement(name = "Bearer Authentication")
    public List<ModuleAndUsersDto> getModuleAndUsersData(
            @RequestParam(name = "nameModule") ModuleNames nameModule,
            @RequestParam(name = "isAdmin", defaultValue = "false") Boolean isAdmin
    ){
        return dashboardService.getModuleAndUsersData(nameModule, isAdmin);
    }

    @Operation(
            summary = "Get data for emotional trend dashboard",
            description = "REST API to get data of the surveys for dashboard"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Request Success"
            )
    })
    @GetMapping("/niko-niko_avg")
    @SecurityRequirement(name = "Bearer Authentication")
    public NikoNikoSummaryData getEmotionalTrendData(){
        return dashboardService.getEmotionalTrendDataAvg();
    }

}