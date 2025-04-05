package com.equipo5.feelflowapp.controller;

import com.equipo5.feelflowapp.dto.badges.BadgeTeamDto;
import com.equipo5.feelflowapp.dto.modules.TwelveStepsResponseAvgDto;
import com.equipo5.feelflowapp.service.module.kudos.KudosService;
import com.equipo5.feelflowapp.service.summary.kudos.SummaryKudosService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@Tag(
        name = "Summary Kudos REST APIs",
        description = "REST APIs in Project to POST AND GET Summary of Kudos surveys"
)
@Slf4j
@RestController
@RequestMapping(path = SummaryKudosController.KUDOS_SUMMARY_PATH,produces = {MediaType.APPLICATION_JSON_VALUE})
public class SummaryKudosController {
    public  static final String KUDOS_SUMMARY_PATH = "/api/v1/summary/kudos";

    private final SummaryKudosService summaryKudosService;

    public SummaryKudosController(SummaryKudosService summaryKudosService) {
        this.summaryKudosService = summaryKudosService;
    }

    @Operation(
            summary = "Get summary averaged data of Kudos surveys",
            description = "REST API to get data Kudos surveys"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Request Success"
            )
    })
    @GetMapping("/summary")
    @SecurityRequirement(name = "Bearer Authentication")
    public List<BadgeTeamDto> getSummary(
            @RequestParam(name = "idModule", required = true) Long idModule,
            @RequestParam(name = "idRegularUser", required = false) UUID idRegularUser
    ){
        return summaryKudosService.getSummary(idModule, idRegularUser);
    }

}
