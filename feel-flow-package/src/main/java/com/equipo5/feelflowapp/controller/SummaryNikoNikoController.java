package com.equipo5.feelflowapp.controller;

import com.equipo5.feelflowapp.domain.enumerations.modules.SurveyStateEnum;
import com.equipo5.feelflowapp.dto.modules.SurveyDto;
import com.equipo5.feelflowapp.dto.summary.SummaryNikoNikoDto;
import com.equipo5.feelflowapp.service.summary.SummaryNikoNikoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Tag(
        name = "Survey REST APIs",
        description = "REST APIs in Project to POST AND GET Surveys"
)
@Slf4j
@RestController
@RequestMapping(path = SummaryNikoNikoController.SUMMARY_NIKO_NIKO_PATH,produces = {MediaType.APPLICATION_JSON_VALUE})
public class SummaryNikoNikoController {
    public  static final String SUMMARY_NIKO_NIKO_PATH = "/api/v1/summary/niko-niko";

    private final SummaryNikoNikoService summaryNikoNikoService;

    @Autowired
    public SummaryNikoNikoController(SummaryNikoNikoService summaryNikoNikoService) {
        this.summaryNikoNikoService = summaryNikoNikoService;
    }


    @Operation(
            summary = "Get Summary of niko niko REST API",
            description = "REST API to get summary of niko niko"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Request Success"
            )
    })
    @GetMapping()
    @SecurityRequirement(name = "Bearer Authentication")
    public List<SummaryNikoNikoDto> getSummary(
            @RequestParam(required = true, name = "idTeam") UUID idTeam,
            @RequestParam(required = false, name = "numberOfMouth") Integer numberOfMouth
    ){
        return summaryNikoNikoService.getSummary(idTeam, numberOfMouth);
    }

}
